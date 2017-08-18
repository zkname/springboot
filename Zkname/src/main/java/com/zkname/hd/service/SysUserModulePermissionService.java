package com.zkname.hd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.hd.dao.SysUserModulePermissionDAO;
import com.zkname.frame.dao.IBaseDAO;
import com.zkname.hd.entity.SysUserModulePermission;
import com.zkname.frame.service.BaseService;

@Service
@Transactional(rollbackFor = Exception.class)
// 注解实现事务，所有异常都回滚；
public class SysUserModulePermissionService extends BaseService<SysUserModulePermission> {

    @Autowired
    private SysUserModulePermissionDAO dao;

    @Transactional(readOnly = true)
    public IBaseDAO<SysUserModulePermission> getDAO() {
        return dao;
    }

    @Transactional(readOnly = true)
    // 非事务处理
    public List<SysUserModulePermission> findRoleIdAll(long roleId) {
        return this.dao.findRoleIdAll(roleId);
    }

    @Transactional(readOnly = true)
    // 非事务处理
    public List<SysUserModulePermission> findAll(long platformId) {
        return this.dao.findAll(platformId);
    }

}