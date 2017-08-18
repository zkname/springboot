package com.zkname.hd.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zkname.frame.dao.BaseDAO;
import com.zkname.hd.entity.SysUserRoleModulePermissionR;

@Repository
public class SysUserRoleModulePermissionRDAO extends BaseDAO<SysUserRoleModulePermissionR> {

	public List<SysUserRoleModulePermissionR> findRoleAll(long id, long platformId) {
		StringBuffer sb=new StringBuffer("select o.*  from sys_user_role_module_permission_r o,sys_user_role sur where sur.id=? and sur.id=o.roleId");
		if(platformId!=1){
			sb.append(" and (sur.platformId=1 or sur.platformId=").append(platformId).append(")");
		}
		return this.find(sb.toString(),id);
	}
	
	
    /**
     * countRoleId:(查询数量).
     */
    public int countRoleId(long roleId){
        String sql="select count(*) from sys_user_role_module_permission_r o where o.roleId=?";
        return super.queryCount(sql,roleId);
    }
}

