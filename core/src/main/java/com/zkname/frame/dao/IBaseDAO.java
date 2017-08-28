package com.zkname.frame.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

/**
 * Function: dao操作
 * Reason:	 TODO ADD REASON
 *
 * 
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-8-11
 */
public interface IBaseDAO<T> {

	
	
	/**
	 * findById(主键查询)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param id
	 * @return 
	 * T
	 * @exception 
	 * @since  1.0.0
	 */
	public T findById(final Object id);
	
	/**
	 * saveOrUpdate(插入数据) 
	 * 
	 * @param args
	 * @exception
	 * @since 1.0.0
	 */
	public void saveOrUpdate(final T arg);
	
	/**
	 * save(插入数据-) 
	 * 
	 * @param args
	 * @exception
	 * @since 1.0.0
	 */
	public void save(final T arg);
	
	/**
	 * update(修改数据) 
	 * 
	 * @param args
	 * @exception
	 * @since 1.0.0
	 */
	public void update(final T arg);
	
	/**
	 * update(修改指定字段)
	 * @param t
	 * @param fields 数据库字段名
	 */
	public void update(T t,String... fields);
	
	/**
	 * deleteId(删除数据) 
	 * 
	 * @param args
	 * @exception
	 * @since 1.0.0
	 */
	public int deleteId(final Object... id);
	
	/**
	 * delete(删除数据) 
	 * 
	 * @param args
	 * @exception
	 * @since 1.0.0
	 */
	public int delete(final T t);
	
    /**
     * 查询原表建立新表
     * @param original_table 原表名
     * @param table_suffix 新建表后缀
     */
	public void createTable(String original_table,String table_suffix);
	
    /**
     * find(返回数组) (这里描述这个方法适用条件 – 可选)
     * 
     * @param sql
     * @param args
     * @return List<?>
     * @exception
     * @since 1.0.0
     */
	public abstract List<?> find(final String sql, RowMapper<?> rowMapper,final Object... args);
}
