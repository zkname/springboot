package com.zkname.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.demo.dao.SysUserRoleModulePermissionRDAO;
import com.zkname.core.dao.IBaseDAO;
import com.zkname.demo.entity.SysUserRoleModulePermissionR;
import com.zkname.core.service.BaseService;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysUserRoleModulePermissionRService extends BaseService<SysUserRoleModulePermissionR> {
	
	@Autowired
	private SysUserRoleModulePermissionRDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public IBaseDAO<SysUserRoleModulePermissionR> getDAO() {
		return dao;
	}
	
	@Transactional(readOnly=true)//非事务处理
	public List<SysUserRoleModulePermissionR> findRoleAll(long id, long platformId) {
	    return this.dao.findRoleAll(id, platformId);
	}
	
	
	   
    /**
     * countRoleId:(查询数量).
     */
	@Transactional(readOnly=true)//非事务处理
    public int countRoleId(long roleId){
        return this.dao.countRoleId(roleId);
    }
}