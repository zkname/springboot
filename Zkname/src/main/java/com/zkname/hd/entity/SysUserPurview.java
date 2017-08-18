package com.zkname.hd.entity;

import java.util.Set;

import com.google.common.collect.Sets;
import com.zkname.hd.entity.base.*;

public class SysUserPurview extends BaseSysUserPurview {

	private static final long serialVersionUID = -3451888062016242057L;

	public SysUserPurview() {
	}

	public SysUserPurview(java.lang.Long id) {
		super(id);
	}
	
	
	public Set<String> getPurviews(){
		return Sets.newHashSet(super.getPurviewText().split(","));
	}
}
