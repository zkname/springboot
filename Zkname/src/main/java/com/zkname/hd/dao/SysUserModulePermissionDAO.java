package com.zkname.hd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.zkname.frame.dao.BaseDAO;
import com.zkname.hd.entity.SysUserModulePermission;

@Repository
public class SysUserModulePermissionDAO extends BaseDAO<SysUserModulePermission> {

	public List<SysUserModulePermission> findAll(long platformId) {
		StringBuffer sb=new StringBuffer("select o.*  from sys_user_module_permission o ");
//		if(platformId!=1){
		sb.append(" where (o.platformId=").append(platformId).append("  or  o.platformId=1 ) ");
//		}
		return this.find(sb.toString());
	}

	public List<SysUserModulePermission> findRoleIdAll(long roleId) {
		StringBuffer sb=new StringBuffer("select o.*  from sys_user_module_permission o,sys_user_role_module_permission_r surmp where surmp.modulePermissionId=o.id and  surmp.roleId=?");
		return this.find(sb.toString(),roleId);
	}

	/**
	 * findByRoleIdModulePermissionId(查询模块权限)
	 * @param roleId 角色
	 * @param modulePermissionId 模块权限
	 * @return
	 */
	public String findByRoleIdModulePermissionId(long roleId, long modulePermissionId) {
		StringBuffer sb=new StringBuffer("select concat(sum.securityName,concat('_',o.securityName)) as securityName  from sys_user_module_permission o,sys_user_role_module_permission_r surmp,sys_user_module sum where sum.id=o.moduleId and surmp.modulePermissionId=o.id and  surmp.roleId=? and surmp.modulePermissionId=?");
		return super.getJdbcTemplate().queryForObject(sb.toString(),new Object[]{roleId,modulePermissionId},new RowMapper<String>(){
			public String mapRow(ResultSet arg0, int arg1) throws SQLException {
				return arg0.getString("securityName");
			}
		});
	}
}
