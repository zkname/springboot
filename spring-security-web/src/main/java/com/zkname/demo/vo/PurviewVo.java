package com.zkname.demo.vo;

import java.io.Serializable;

/**
 * 
 * 
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-1-16
 */
public class PurviewVo implements Serializable, Cloneable{

	private static final long serialVersionUID = -7199572842169572255L;

	//
	private long id;
	
	private String name;
	
	private long parentId;
	
	private String securityName;
	
	private String resourceValue;
	
	public PurviewVo(){
		
	}
	public PurviewVo(long id,String name,long parentId,String securityName,String resourceValue){
		this.id=id;
		this.name=name;
		this.parentId=parentId;
		this.securityName=securityName;
		this.resourceValue=resourceValue;
	}
	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	public String getResourceValue() {
		return resourceValue;
	}
	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}
}

