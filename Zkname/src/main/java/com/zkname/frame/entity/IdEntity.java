package com.zkname.frame.entity;

/**
 * ClassName:IdEntity
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhangk@autoradio.cn
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-7-27
 */
/**
 * IdEntity 2011-7-27 上午11:18:03
 * 
 * @version 1.0.0
 * 
 */
public abstract class IdEntity<ID> implements java.io.Serializable{

	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 6259183113234841098L;

	public abstract ID getId();
	
	public abstract void setId(ID id);
}
