package com.zkname.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.demo.dao.SysUserUserRoleRDAO;
import com.zkname.core.dao.IBaseDAO;
import com.zkname.demo.entity.SysUserUserRoleR;
import com.zkname.core.service.BaseService;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysUserUserRoleRService extends BaseService<SysUserUserRoleR> {
	
	@Autowired
	private SysUserUserRoleRDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public IBaseDAO<SysUserUserRoleR> getDAO() {
		return dao;
	}
	
	@Transactional(readOnly=true)//非事务处理
	public List<SysUserUserRoleR> findUserAll(long userId, long platformId){
	    return this.dao.findUserAll(userId, platformId);
	}
	
	@Transactional(readOnly=true)//非事务处理
	public SysUserUserRoleR getSysUserUserRoleR(long roleId, long userId) {
	    return this.dao.getSysUserUserRoleR(roleId, userId);
	}
	
	/**
	 * 
	 * countRoleId:(查询组下是否存在用户).
	 */
	@Transactional(readOnly=true)//非事务处理
	public int countRoleId(long roleId){
	    return this.dao.countRoleId(roleId);
	}
}