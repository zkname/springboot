package com.zkname.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zkname.core.dao.BaseDAO;
import com.zkname.demo.entity.SysUserUserRoleR;

@Repository
public class SysUserUserRoleRDAO extends BaseDAO<SysUserUserRoleR> {

    public List<SysUserUserRoleR> findUserAll(long userId, long platformId) {
        StringBuffer sb = new StringBuffer("select o.*  from sys_user_user_role_r o,sys_user_role sur where o.userId=? and sur.id=o.roleId ");
        if (platformId != 1) {
            sb.append(" and sur.platformId='").append(platformId).append("'");
        }
        return this.find(sb.toString(), userId);
    }

    /**
     * 查询权限
     * @param roleId
     * @param userId
     * @return
     */
    public SysUserUserRoleR getSysUserUserRoleR(long roleId, long userId) {
        String sql = "select o.*  from sys_user_user_role_r o where o.roleId=? and o.userId=?";
        return this.findBy(sql, roleId, userId);
    }
    
    
    /**
     * 
     * countUser:(查询组下是否存在用户).
     */
    public int countRoleId(long roleId){
        String sql = "select count(*) from sys_user_user_role_r o where o.roleId=?";
        return this.queryCount(sql, roleId);
    }
}
