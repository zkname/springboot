package com.zkname.credit.card.entity;

import com.zkname.credit.card.entity.base.BaseSysUser;
import com.zkname.credit.card.session.LoginUser;

public class SysUser extends BaseSysUser {

	private static final long serialVersionUID = -3451888062016242057L;

	public SysUser() {
	}

	public SysUser(java.lang.Long id) {
		super(id);
	}
	
	public LoginUser getLoginUser(){
		LoginUser suv=new LoginUser();
		suv.setId(this.getId());
		suv.setPassword(this.getPassword());
		suv.setUsername(this.getUsername());
		suv.setEmail(this.getEmail());
		suv.setRole(this.getRole());
		return suv;
	}
}