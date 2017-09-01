package com.zkname.demo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Lists;

/**
 * 用户权限
 * @version  
 */
public class SysUserVo implements Serializable{

	private static final long serialVersionUID = -3742400238356120200L;
	
	private long id;
	private String userName;
	private String password;
	private String email;
	private Date createTime;
	private Date updateTime;
	private String deleStatus;
	private int creatorId;
	private Date loginTime;
	private String realName;
	//用户角色编码
	private String roleCode;
	//所属平台
	private int platformId;

	private ArrayList<String> grantedAuthoritys;

	
	public void setUserPurviewTemplate(HashMap<String, String> userPurviewTemplate) {
		ArrayList<String> auths = Lists.newArrayList();
		if(userPurviewTemplate!=null){
			Set<String> key = userPurviewTemplate.keySet();
	        for (Iterator<String> it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            auths.add(s);
	        }
		}
		this.grantedAuthoritys=auths;
	}
	
	public void setUserPurviewTemplate(Set<String> key) {
		ArrayList<String> auths = Lists.newArrayList();
		if(key!=null && key.size()>0){
	        for (Iterator<String> it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            auths.add(s);
	        }
		}
		this.grantedAuthoritys=auths;
	}

	public ArrayList<String> getGrantedAuthoritys() {
		return grantedAuthoritys;
	}



	public void setGrantedAuthoritys(ArrayList<String> grantedAuthoritys) {
		this.grantedAuthoritys = grantedAuthoritys;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleStatus() {
		return deleStatus;
	}

	public void setDeleStatus(String deleStatus) {
		this.deleStatus = deleStatus;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}
	
}