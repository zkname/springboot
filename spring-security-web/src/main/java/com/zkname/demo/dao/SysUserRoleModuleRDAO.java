package com.zkname.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zkname.core.dao.BaseDAO;
import com.zkname.demo.entity.SysUserRoleModuleR;

@Repository
public class SysUserRoleModuleRDAO extends BaseDAO<SysUserRoleModuleR> {

	public List<SysUserRoleModuleR> findRoleAll(long id, long platformId) {
		StringBuffer sb=new StringBuffer("select o.* from sys_user_role_module_r o,sys_user_role sur where sur.id=? and sur.id=o.roleId");
		if(platformId!=1){
			sb.append(" and (sur.platformId=1 or sur.platformId=").append(platformId).append(")");
		}
		return this.find(sb.toString(),id);
	}
	
    /**
     * countRoleId:(查询数量).
     */
    public int countRoleId(long roleId){
        String sql="select count(*) from sys_user_role_module_r o where o.roleId=?";
        return super.queryCount(sql,roleId);
    }
}

