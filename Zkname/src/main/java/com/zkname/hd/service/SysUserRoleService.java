package com.zkname.hd.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.zkname.hd.dao.SysUserRoleDAO;
import com.zkname.frame.dao.IBaseDAO;
import com.zkname.hd.entity.SysUserRole;
import com.zkname.hd.entity.SysUserRoleModulePermissionR;
import com.zkname.hd.entity.SysUserRoleModuleR;
import com.zkname.frame.service.BaseService;
import com.zkname.frame.util.ParamType;
import com.zkname.hd.util.exception.ActionException;
import com.zkname.hd.util.exception.DaoException;

@Service
@Transactional(rollbackFor = Exception.class)
// 注解实现事务，所有异常都回滚；
public class SysUserRoleService extends BaseService<SysUserRole> {

    @Autowired
    private SysUserRoleDAO dao;

    @Autowired
    private SysUserRoleModuleRService sysUserRoleModuleRService;

    @Autowired
    private SysUserRoleModulePermissionRService isysSysUserRoleModulePermissionRService;

    @Autowired
    private SysUserService iadminSysUserService;

    @Autowired
    private SysUserUserRoleRService sysUserUserRoleRService;

    @Autowired
    private SysUserRoleModuleRService sysUserRoleModuleRsService;

    // 开发者
    public static final String ROLE_CODE_DEVELOPERS = "10001000";
    // 开发者id
    public static final long ROLE_CODE_DEVELOPERS_ID = 101l;
    // 子账户
    public static final String ROLE_CODE_SUBACCOUNT = "100010001000";
    // 子账户id
    public static final long ROLE_CODE_SUBACCOUNT_ID = 102l;

    @Transactional(readOnly = true)
    // 非事务处理
    public IBaseDAO<SysUserRole> getDAO() {
        return dao;
    }

    /**
     * updateStatus(修改状态)
     * (这里描述这个方法适用条件 – 可选)
     * @param id
     * @return 
     * int
     * @exception 
     * @since  1.0.0
     */
    public int updateStatus(Long[] ids, int type) {
        if (ids == null)
            return 0;
        int i = 0;
        for (Long id : ids) {
            SysUserRole sysUserRole = dao.findById(id);
            if (sysUserRole != null) {
                sysUserRole.setDeleStatus(Integer.toString(type));
                this.saveOrUpdate(sysUserRole);
                i++;
            }
        }
        return i;
    }

    /**
     * 新增
     */
    public void save(Long[] moduleId, Long[] purviewId, SysUserRole entity) {
        if (dao.getCountRole(entity.getId() == null ? 0 : entity.getId(), entity.getName(), entity.getPlatformId()) > 0) {
            throw new DaoException("角色名已存在！");
        }

        SysUserRole fObject = dao.getNewCode(entity.getParentId());
        if (fObject == null) {
            fObject = this.findById(entity.getParentId());
            entity.setRoleCode(fObject.getRoleCode() + "1000");
        } else {
            int code = ParamType.getint(fObject.getRoleCode().substring(fObject.getRoleCode().length() - 4));
            code += 1;
            if (code % 1000 > 999) {
                throw new DaoException("角色错误！");
            }
            entity.setRoleCode(fObject.getRoleCode().substring(0, fObject.getRoleCode().length() - 4) + Integer.toString(code));
        }
        this.saveOrUpdate(entity);

        // 添加模块权限
        if (moduleId == null || moduleId.length < 1)
            return;
        Set<Long> strSet = Sets.newHashSet();
        CollectionUtils.addAll(strSet, moduleId);
        for (Long obj : strSet) {
            if (obj == null || obj.intValue() == 0)
                continue;
            SysUserRoleModuleR suurr = new SysUserRoleModuleR();
            suurr.setRoleId(entity.getId());
            suurr.setModuleId(obj);
            sysUserRoleModuleRService.saveOrUpdate(suurr);
        }

        // 添加功能权限
        if (purviewId == null || purviewId.length < 1)
            return;
        strSet = Sets.newHashSet();
        CollectionUtils.addAll(strSet, purviewId);
        for (Long obj : strSet) {
            if (obj == null || obj.intValue() == 0)
                continue;
            SysUserRoleModulePermissionR suurr = new SysUserRoleModulePermissionR();
            suurr.setRoleId(entity.getId());
            suurr.setModulePermissionId(obj);
            isysSysUserRoleModulePermissionRService.saveOrUpdate(suurr);
        }
    }

    /**
     * 模块权限修改
     * @param moduleId
     * @param purviewId
     * @param entity
     */
    private void updateModulePermission(Long[] moduleId, Long[] purviewId, SysUserRole entity) {

        // 模块修改=====================================
        Set<Long> strSet = Sets.newHashSet();
        if (moduleId != null)
            CollectionUtils.addAll(strSet, moduleId);

        List<SysUserRoleModuleR> list = sysUserRoleModuleRService.findRoleAll(entity.getId(), entity.getPlatformId());

        for (int i = 0; i < list.size(); i++) {
            SysUserRoleModuleR sysUserRoleModuleR = list.get(i);
            if (strSet.contains(sysUserRoleModuleR.getModuleId())) {
                strSet.remove(sysUserRoleModuleR.getModuleId());
                list.remove(i);
                i--;
            }
        }
        // 删除为选择的权限
        for (int i = 0; i < list.size(); i++) {
            SysUserRoleModuleR sysUserUserRoleR = list.get(i);
            sysUserRoleModuleRService.deleteId(sysUserUserRoleR.getId());
        }
        for (Long obj : strSet) {
            if (obj == null || obj.intValue() == 0)
                continue;
            SysUserRoleModuleR suurr = new SysUserRoleModuleR();
            suurr.setRoleId(entity.getId());
            suurr.setModuleId(obj);
            sysUserRoleModuleRService.saveOrUpdate(suurr);
        }
        // 模块权限修改=====================================
        strSet = Sets.newHashSet();
        if (purviewId != null)
            CollectionUtils.addAll(strSet, purviewId);

        List<SysUserRoleModulePermissionR> listr = isysSysUserRoleModulePermissionRService.findRoleAll(entity.getId(), entity.getPlatformId());

        for (int i = 0; i < listr.size(); i++) {
            SysUserRoleModulePermissionR sysUserRoleModuleR = listr.get(i);
            if (strSet.contains(sysUserRoleModuleR.getModulePermissionId())) {
                strSet.remove(sysUserRoleModuleR.getModulePermissionId());
                listr.remove(i);
                i--;
            }
        }

        // 删除为选择的权限
        for (int i = 0; i < listr.size(); i++) {
            SysUserRoleModulePermissionR sysUserUserRoleR = listr.get(i);
            isysSysUserRoleModulePermissionRService.deleteId(sysUserUserRoleR.getId());
        }

        for (Long obj : strSet) {
            if (obj == null || obj.intValue() == 0)
                continue;
            SysUserRoleModulePermissionR suurr = new SysUserRoleModulePermissionR();
            suurr.setRoleId(entity.getId());
            suurr.setModulePermissionId(obj);
            isysSysUserRoleModulePermissionRService.saveOrUpdate(suurr);
        }
    }

    /**
     * 修改操作
     */
    public void update(Long[] moduleId, Long[] purviewId, SysUserRole entity, Long parentId) {
        if (dao.getCountRole(entity.getId(), entity.getName(), entity.getPlatformId()) > 0) {
            throw new DaoException("角色名已存在！");
        }
        // 老code
        String oldRoleCode = entity.getRoleCode();
        // 判断是否需要修改编号code
        if (!entity.getParentId().equals(parentId) && parentId.intValue() != -1) {
            entity.setParentId(parentId);
            SysUserRole fObject = dao.getNewCode(entity.getParentId());
            if (fObject == null) {
                fObject = this.findById(entity.getParentId());
                entity.setRoleCode(fObject.getRoleCode() + "1000");
            } else {
                int code = ParamType.getint(fObject.getRoleCode().substring(fObject.getRoleCode().length() - 4));
                code += 1;
                if (code % 1000 > 999) {
                    throw new DaoException("角色错误！");
                }
                entity.setRoleCode(fObject.getRoleCode().substring(0, fObject.getRoleCode().length() - 4) + Integer.toString(code));
            }
            // 修改code
            this.updateCode(oldRoleCode, entity.getRoleCode());
        }
        this.saveOrUpdate(entity);

        // 模块权限修改
        updateModulePermission(moduleId, purviewId, entity);
    }

    private void updateCode(String oldRoleCode, String newRoleCode) {
        // 修改code
        iadminSysUserService.updateCode(oldRoleCode, newRoleCode);
        dao.updateCode(oldRoleCode, newRoleCode);
    }

    /**
     * 
     * findByName(查询权限是否已存在)
     * (这里描述这个方法适用条件 – 可选)
     * @param name
     * @return 
     * SysUserRole
     * @exception 
     * @since  1.0.0
     */
    @Transactional(readOnly = true)
    // 非事务处理
    public SysUserRole findByName(long platformId, String name) {
        return dao.findByName(platformId, name);
    }

    public void delete(java.lang.Long... ids) {
        for (java.lang.Long id : ids) {

            SysUserRole sur = this.findById(id);

            if (dao.getCountRoleCode(sur.getRoleCode()) > 0) {
                throw new ActionException("角色存在子角色，请先删除子角色！");
            }
            if (sysUserUserRoleRService.countRoleId(sur.getId()) > 0) {
                throw new ActionException("角色内存在用户请先移出用户！");
            }

            if (sysUserRoleModuleRsService.countRoleId(sur.getId()) > 0 || isysSysUserRoleModulePermissionRService.countRoleId(sur.getId()) > 0) {
                throw new ActionException("角色内存在权限！");
            }
            this.getDAO().deleteId(id);
        }
    }

    // 查询平台下的所有角色
    @Transactional(readOnly = true)
    // 非事务处理
    public List<SysUserRole> findLike(long platformId, String code) {
        return this.dao.findLike(platformId, code);
    }

    @Transactional(readOnly = true)// 非事务处理
    public List<SysUserRole> findAll(long platformId, String notCode) {
        return this.dao.findAll(platformId, notCode);
    }
    
    @Transactional(readOnly = true)// 非事务处理
    public List<SysUserRole> findAll(long platformId, long notId) {
        return this.dao.findAll(platformId, notId);
    }
    

}