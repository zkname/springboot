package com.zkname.frame.dao.sql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zkname.frame.dao.sql.SqlFactory.SqlType;
import com.zkname.frame.util.classinfo.ClassInfo;
import com.zkname.frame.util.classinfo.Constants;
import com.zkname.frame.util.classinfo.ReflectionCache;
import com.zkname.frame.util.exception.DaoException;
import com.zkname.frame.util.exception.ExceptionUtils;

/**
 * ClassName:SqlBean
 * Function: 用于封装对象的字段
 * Reason:	 TODO ADD REASON
 *
 * @author   zhangk@autoradio.cn
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-7-27
 */
public class SqlBean {
	private HashMap<SqlType, String> sqlMap=Maps.newHashMap();//保存sql语句
	private String tableName;//表名
	private List<String> ids=Lists.newArrayList();//主键
	private boolean isAuto;//主键是否自增
	private List<String> other = Lists.newArrayList();//其他字段

	public SqlBean(Class<?> entityClass){
		while (!entityClass.isAnnotationPresent(Table.class)) {
			entityClass=entityClass.getSuperclass();
			if(entityClass==Object.class){
				throw ExceptionUtils.unchecked(new DaoException("未找到表缺少注解:Table"));
			}
		}
		List<Boolean> notId=Lists.newArrayList();
		try {
			ClassInfo classInfo=ReflectionCache.putClassInfo(entityClass,Constants.FIELD);
			Map<String, Field> map=classInfo.getFieldMap();
			map.forEach((k,field)->{
				if(field!=null && field.isAnnotationPresent(Column.class)){
					Column column=field.getAnnotation(Column.class);
					if(field.isAnnotationPresent(Id.class)){
						String id=column.name().trim();
						ids.add(id);
						notId.add(true);
						GeneratedValue gv=field.getAnnotation(GeneratedValue.class);
						if(gv!=null && gv.strategy()==GenerationType.AUTO){
							this.setAuto(true);
						}
					}else{
						other.add(column.name().trim());
					}
				}
			});
			if(entityClass.isAnnotationPresent(Table.class)){
				this.tableName=entityClass.getAnnotation(Table.class).name().toLowerCase().trim();
			}
			if(this.tableName==null){
				throw ExceptionUtils.unchecked(new DaoException("未找到表缺少注解:Table"));
			}
			if(notId.size()<1){
				throw ExceptionUtils.unchecked(new DaoException("未找到主键缺少注解:id"));
			}
		}catch (Exception e) {
			throw ExceptionUtils.unchecked(new DaoException("初始化SqlBean错误",e));
		}
	}
	
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	/**
	 * other
	 * 
	 * @return the other
	 * @since 1.0.0
	 */
	public List<String> getOther() {
		return other;
	}

	/**
	 * @param other
	 *            the other to set
	 */
	public void setOther(List<String> other) {
		this.other = other;
	}
	
	
	/**
	 * tableName
	 * @return  the tableName
	 * @since   1.0.0
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * sqlMap
	 * @return  the sqlMap
	 * @since   1.0.0
	 */
	public HashMap<SqlType, String> getSqlMap() {
		return sqlMap;
	}

	/**
	 * @param sqlMap the sqlMap to set
	 */
	public void setSqlMap(HashMap<SqlType, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	public boolean isAuto() {
		return isAuto;
	}



	public void setAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}
}
