package com.zkname.core.dao.row;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import com.zkname.core.util.classinfo.ClassInfo;
import com.zkname.core.util.classinfo.Constants;
import com.zkname.core.util.classinfo.ReflectionCache;


/**
 * @pengle 对查询数据结果集进行封装bean
 */
public class AutoBoxingRowMapper<T> extends BeanPropertyRowMapper<T> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Class<?> beanClass;// 即将要进行包装的bean的Class

	private Map<String, Field> fieldMap = new HashMap<String, Field>();// 即将要进行包装的bean的属性描述,以Map形式保存

	private boolean isComment=false;
	
	public Class<?> getBeanClass() {
		return beanClass;
	}

	public Map<String, Field> getFieldMap() {
		return fieldMap;
	}

	/**
	 * 自动装配sql查询结果集的各列值到beanClass所指定的bean中的对应属性上,满足toLowerCase(columnName) =
	 * toLowerCase(propertyName)时进行装配
	 * 
	 * @param beanClass - 要装配的bean的java.lang.Class
	 */
	public AutoBoxingRowMapper(Class<?> beanClass) {
		super();
		this.beanClass = beanClass;
		while (beanClass != Object.class ) {
			if(beanClass.isAnnotationPresent(Table.class)){
				isComment=true;
				break;
			}
			beanClass=beanClass.getSuperclass();
		}
		if(!isComment){
			return;
		}
		//只获取本类的所有属性，不获取父类的
		ClassInfo classInfo=ReflectionCache.putClassInfo(this.beanClass,Constants.FIELD);
		Map<String, Field> map=classInfo.getFieldMap();
		for(Map.Entry<String, Field> entry:map.entrySet()){
			Field field=entry.getValue();
			Column column=field.getAnnotation(Column.class);
			if(column!=null){
				this.getFieldMap().put(column.name().toLowerCase().trim(), field);
			}else{
				this.getFieldMap().put(field.getName().toLowerCase().trim(), field);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		if(!isComment){
			return super.mapRow(rs, rowNum);
		}
		T defaultInstance = null;
		try {
			defaultInstance = (T) this.getBeanClass().newInstance();// 获取bean的实例
		} catch (Exception e) {
			logger.error("获取bean的实例错误",e);
		}
		if (defaultInstance == null) {
			logger.error("attempt to new a instance of class named : " + this.getBeanClass().getName() + " but failed!!!");
			return null;
		}
		// 获取元数据
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Field field = null;
		try {
			for (int i = 1; i <= columnCount; i++) {
				String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));// 获取resultset结果集中第i列对应的列名
				Object obj = getColumnValue(rs, i, key);// 获取resultset结果集中第i列对应的值

				field = this.getFieldMap().get(key);
				if (field == null) {// 当前列名key对应的属性在bean中找不到,给予警告
					logger.warn("no corresponding property defined in class : "+ this.getBeanClass().getName()+ " for sql query resultset column '" + key+ "' !!!");
					continue;
				} else {//当前列名key对应的属性在bean中找到,则调用其setXxx方法设置其值
					field.setAccessible(true); 
					field.set(defaultInstance,obj);
				}
			}
		} catch (Exception e) {
			logger.error("写入对象错误",e);
		}
		return defaultInstance;
	}

	protected String getColumnKey(String columnName) {
		return columnName == null ? null : columnName.toLowerCase().trim();
	}

	protected Object getColumnValue(ResultSet rs, int index, String columnName)throws SQLException {
		Object val = null;
		Field field = this.getFieldMap().get(columnName);
		if (field != null) {
			Class<?> propertyTypeClass = field.getType();
			String className = propertyTypeClass.getName();
			if (className.equals("int")
					|| className.equals(Integer.class.getName())) {
				val = rs.getInt(index);
				return val;
			} else if (className.equals("short")
					|| className.equals(Short.class.getName())) {
				val = rs.getShort(index);
				return val;
			} else if (className.equals("byte")
					|| className.equals(Byte.class.getName())) {
				val = rs.getByte(index);
				return val;
			} else if (className.equals("long")
					|| className.equals(Long.class.getName())) {
				val = rs.getLong(index);
				return val;
			} else if (className.equals("float")
					|| className.equals(Float.class.getName())) {
				val = rs.getFloat(index);
				return val;
			} else if (className.equals("double")
					|| className.equals(Double.class.getName())) {
				val = rs.getDouble(index);
				return val;
			} else if (className.equals("char")
					|| className.equals(Character.class.getName())) {
				val = rs.getString(index);
				return val;
			} else if (className.equals(String.class.getName())) {
				val = rs.getString(index);
				return val;
			} else if (className.equals(java.util.Date.class.getName())) {
				val = rs.getTimestamp(index);
				return val;
			} else if (className.equals(java.math.BigDecimal.class
					.getName())) {
				val = rs.getBigDecimal(index);
				return val;
			} else {// Object
				val = rs.getObject(index);
				return val;
			}
		} else {
			val = JdbcUtils.getResultSetValue(rs, index);
		}
		return val;
	}
}
