package com.zkname.frame.dao.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;

import com.google.common.collect.Sets;
import com.zkname.frame.util.classinfo.ClassInfo;
import com.zkname.frame.util.classinfo.Constants;
import com.zkname.frame.util.classinfo.ReflectionCache;
import com.zkname.frame.util.exception.DaoException;
import com.zkname.frame.util.exception.ExceptionUtils;

/**
 * ClassName:SqlFactory
 * Function: sql 工厂
 * 
 * Reason:	 TODO ADD REASON
 *
 * @author   zhangk@autoradio.cn
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-7-27
 */
public class SqlFactory {
	
	enum SqlType {
		FIND_All, // 所有
		FIND_BY_ID, // 主键查询
		SAVE, // 新增
		UPDATE, // 更新
		DELETE// 删除
	}
	
	//保存SqlBean属性
	private HashMap<Class<?>, SqlBean> sqlBeanMap;
	
	public SqlFactory(){
		sqlBeanMap=new HashMap<Class<?>, SqlBean>();
	}
	
	/**
	 * get(获取sql bean 进行sql 封装)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * SqlBean
	 * @exception 
	 * @since  1.0.0 
	 */
	public SqlBean get(Class<?> entityClass){
		SqlBean sqlBean=sqlBeanMap.get(entityClass);
		if(sqlBean==null){
			synchronized (SqlFactory.class) {
				if(sqlBean==null){
					sqlBean=new SqlBean(entityClass);
					//生成sql语句
					SqlUtil.creatSql(sqlBean);
					sqlBeanMap.put(entityClass,sqlBean);
				}
			}
		}
		return sqlBean;
	}

	/**
	 * getById(主键sql)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String findById(Class<?> entityClass) {
		//获取sql
		return get(entityClass).getSqlMap().get(SqlType.FIND_BY_ID);
	}
	
	/**
	 * save(新增)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getSaveSql(Class<?> entityClass) {
		//获取sql
		return get(entityClass).getSqlMap().get(SqlType.SAVE);
	}
	

	/**
	 * save(更新指定字段)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getUpdateSql(Class<?> entityClass,String... fields) {
		//获取sql
		if(fields==null || fields.length<1){
			return getUpdateSql(entityClass);
		}
		return SqlUtil.getUpdateSql(get(entityClass), fields);
	}
	
	
	/**
	 * save(更新)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getUpdateSql(Class<?> entityClass) {
		//获取sql
		return get(entityClass).getSqlMap().get(SqlType.UPDATE);
	}
	
	/**
	 * save(删除)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getDeleteSql(Class<?> entityClass) {
		//获取sql
		return get(entityClass).getSqlMap().get(SqlType.DELETE);
	}
	
	/**
	 * findAll(查询所有)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String findAll(Class<?> entityClass) {
		return get(entityClass).getSqlMap().get(SqlType.FIND_All);
	}
	
	/**
	 * getSaveParam(获取插入对象的属性值)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entity
	 * @param entityClass
	 * @return 
	 * Object[]
	 * @exception 
	 * @since  1.0.0
	 */
	public Object [] getSaveParam(Object entity,Class<?> entityClass) {
		List<Object> list=new ArrayList<Object>();
		try {
			ClassInfo classInfo=ReflectionCache.putClassInfo(entityClass,Constants.FIELD);
			Map<String, Field> map=classInfo.getFieldMap();
			map.forEach((k,field)->{
				try {
					if(field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(Column.class)){
						field.setAccessible(true); 
						list.add(field.get(entity));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}catch (Exception e) {
			throw ExceptionUtils.unchecked(new DaoException(entity.getClass().getCanonicalName()+"属性获取失败！"));
		}
		return list.toArray();
	}
	
	
	/** 
	 * getUpdateParam(获取更新对象的属性值)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entity
	 * @param entityClass
	 * @return 
	 * Object[]
	 * @exception 
	 * @since  1.0.0
	 */
	public Object [] getUpdateParam(Object entity,Class<?> entityClass,String... fields) {
		List<Object> list=new ArrayList<Object>();
		try {
			ClassInfo classInfo=ReflectionCache.putClassInfo(entityClass,Constants.FIELD);
			Map<String, Field> map=classInfo.getFieldMap();
			List<Object> idValues=new ArrayList<Object>();
			Set<String> s=Sets.newHashSet(fields);
			map.forEach((k,field)->{
				try {
					if(field.isAnnotationPresent(Id.class)){
						field.setAccessible(true); 
						idValues.add(field.get(entity));
						return;
					}
					if(field.isAnnotationPresent(Column.class)){
						Column column=field.getAnnotation(Column.class);
						if(s.size()>0 && !s.contains(column.name())){
							return;
						}
						field.setAccessible(true); 
						list.add(field.get(entity));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			list.addAll(idValues);
		}catch (Exception e) {
			throw ExceptionUtils.unchecked(new DaoException(entity.getClass().getCanonicalName()+"属性获取失败！"));
		}
		return list.toArray();
	}

}
