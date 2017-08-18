package com.zkname.frame.service;



/**
 * service 层继承
 *
 * @version
 * @since Ver 1.1
 * @Date 2012-6-19
 */
public interface IBaseService<T> {
    
	/**
	 * 主键查询
	 * 
	 * @param id
	 */
	public T findById(Object id);

	/**
	 * 添加修改
	 * 
	 * @param entitys
	 */
	public void saveOrUpdate(T entity);

	/**
	 * delete(主键删除)
	 * 
	 * @param ids
	 */
	public void deleteId(Object... id);
	
	/**
	 * delete(删除)
	 * 
	 * @param ids
	 */
	public void delete(T id);

    /**
     * 增加
     */
    public void save(T entity);
    
    /**
     * 修改
     */
    public void update(T entity);
    
    /**
     * 修改 固定字段
     */
    public void update(T entity,String... fields);
}
