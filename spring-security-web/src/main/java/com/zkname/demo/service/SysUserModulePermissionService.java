package com.zkname.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.demo.dao.SysUserModulePermissionDAO;
import com.zkname.core.dao.IBaseDAO;
import com.zkname.demo.entity.SysUserModulePermission;
import com.zkname.core.service.BaseService;

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