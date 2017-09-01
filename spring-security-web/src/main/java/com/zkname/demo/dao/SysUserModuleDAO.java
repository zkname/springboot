package com.zkname.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.zkname.core.dao.BaseDAO;
import com.zkname.demo.entity.SysUserModule;

@Repository
public class SysUserModuleDAO extends BaseDAO<SysUserModule> {
   
	/**
	 * findAll(查询模块权限)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param platformId
	 * @return 
	 * List<Map<String,String>>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<Map<String,String>> findAll(long platformId){
		StringBuffer sb=new StringBuffer("select o.securityName as securityName,o.resourceValue as resourceValue from sys_user_module o where o.deleStatus=1 and o.resourceValue is not null and (o.platformId=1 or o.platformId=?)");
		
		if(platformId!=1){
			sb.append("  and  o.creatorId!=0");
		}
		
		return super.getJdbcTemplate().query(sb.toString(), new Object[]{platformId},new RowMapper<Map<String,String>>(){
			public Map<String, String> mapRow(ResultSet arg0, int arg1) throws SQLException {
				Map<String,String> map=Maps.newHashMap();
				map.put("securityName",arg0.getString("securityName"));
				map.put("resourceValue",arg0.getString("resourceValue"));
				return map;
			}
		});
	} 
	
	/**
	 * findPermissionAll(查询功能权限)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param platformId
	 * @return 
	 * List<Map<String,String>>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<Map<String,String>> findPermissionAll(long platformId){
		StringBuffer sb=new StringBuffer("select o.securityName as msecurityName,sump.securityName as securityName,sump.resourceValue as resourceValue from sys_user_module_permission sump,sys_user_module o where sump.deleStatus=1 and o.id=sump.moduleId and (o.deleStatus=1 or o.deleStatus='-' ) and (sump.platformId=1 or sump.platformId=?) ");
		return super.getJdbcTemplate().query(sb.toString(),new Object[]{platformId},new RowMapper<Map<String,String>>(){
			public Map<String, String> mapRow(ResultSet arg0, int arg1) throws SQLException {
				Map<String,String> map=Maps.newHashMap();
				map.put("securityName",arg0.getString("msecurityName")+"_"+arg0.getString("securityName"));
				map.put("resourceValue",arg0.getString("resourceValue"));
				return map;
			}
		});
	}

	
	/**
	 * findUserAll(获取用户模块权限)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param userId
	 * @param platformId
	 * @return 
	 * List<String>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<String> findUserAll(long userId,long platformId){
		StringBuffer sb=new StringBuffer("select o.securityName as securityName from sys_user_module o,sys_user_role_module_r surmr,sys_user_user_role_r suurr,sys_user_role sur where suurr.userId=? and suurr.roleId=sur.id and sur.deleStatus=1 and suurr.roleId=surmr.roleId and surmr.moduleId=o.id and (o.deleStatus='1' or o.deleStatus='-') and (o.platformId=1 or o.platformId=?)");
		return super.getJdbcTemplate().query(sb.toString(),new Object[]{userId,platformId},new RowMapper<String>(){
			public String mapRow(ResultSet arg0, int arg1) throws SQLException {
				return arg0.getString("securityName");
			}
		});
	}
	
	/**
	 * findUserParentProgram(获取用户资讯栏目标签)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param userId
	 * @param platformId
	 * @return 
	 * List<String>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<String> findUserParentProgram(long userId,long parentId,long platformId){
		StringBuffer sb=new StringBuffer("select o.securityName as securityName from sys_user_module o,sys_user_role_module_r surmr,sys_user_user_role_r suurr where suurr.userId=? and suurr.roleId=surmr.roleId and surmr.moduleId=o.id and  o.parentId=?  and (o.platformId=1 or o.platformId=?) ORDER BY o.securityName DESC");
		return super.getJdbcTemplate().query(sb.toString(),new Object[]{userId,parentId,platformId},new RowMapper<String>(){
			public String mapRow(ResultSet arg0, int arg1) throws SQLException {
				return arg0.getString("securityName");
			}
		});
	}
	
	/**
	 * findUserPermissionAll(获取用户模块操作权限)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param userId
	 * @param platformId
	 * @return 
	 * List<String>
	 * @exception 
	 * @since  1.0.0  
	 */
	public List<String> findUserPermissionAll(long userId,long platformId){
		StringBuffer sb=new StringBuffer("select sum.securityName as msecurityName,o.securityName as securityName from sys_user_module_permission o,sys_user_module sum,sys_user_role_module_permission_r surmr,sys_user_user_role_r suurr,sys_user_role sur where sum.id=o.moduleId and (sum.deleStatus=1 or sum.deleStatus='-' ) and suurr.userId=? and suurr.roleId=surmr.roleId  and sur.id=suurr.roleId and sur.deleStatus=1 and surmr.modulePermissionId=o.id and o.deleStatus=1 and (o.platformId=1 or o.platformId=?)");
		return super.getJdbcTemplate().query(sb.toString(),new Object []{userId,platformId},new RowMapper<String>(){
			public String mapRow(ResultSet arg0, int arg1) throws SQLException {
				return arg0.getString("msecurityName")+"_"+arg0.getString("securityName");
			}
		});
	}
	
	
	/**
	 * findMenuAll(查询系统菜单)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param platformId
	 * @return 
	 * List<Map<String,String>>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<Map<String,String>> findMenuAll(long platformId){
		StringBuffer sb=new StringBuffer("select o.id as id ,o.name as name,o.securityName as securityName,o.resourceValue as resourceValue,o.orderNum as orderNum,o.parentId as parentId,o.deleStatus as deleStatus from sys_user_module o ");
		sb.append(" where  (o.deleStatus='1' or o.deleStatus='-') and (o.platformId=").append(platformId).append(" or o.platformId=1 ) ");
		if(platformId!=1){
			sb.append(" and o.creatorId!=0 ");
		}
		sb.append(" order by o.orderNum,o.parentId");
		return super.getJdbcTemplate().query(sb.toString(),new RowMapper<Map<String,String>>(){
			public Map<String, String> mapRow(ResultSet arg0, int arg1) throws SQLException {
				Map<String,String> map=Maps.newHashMap();
				map.put("name",arg0.getString("name"));
				map.put("securityName",arg0.getString("securityName"));
				map.put("resourceValue",arg0.getString("resourceValue"));
				map.put("orderNum",arg0.getString("orderNum"));
				map.put("parentId",arg0.getString("parentId"));
				map.put("id",arg0.getString("id"));
				map.put("deleStatus",arg0.getString("deleStatus"));
				return map;
			}
		});
	}

	/**
	 * findRoleIdMenuAll(查询模块权限)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param roleId
	 * @return 
	 * List<Map<String,String>>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<Map<String, String>> findRoleIdMenuAll(long roleId) {
		StringBuffer sb=new StringBuffer("select o.id as id ,o.name as name,o.securityName as securityName,o.resourceValue as resourceValue,o.orderNum as orderNum,o.parentId as parentId,o.deleStatus as deleStatus from sys_user_module o,sys_user_role_module_r surm  ");
		sb.append(" where  (o.deleStatus='1' or o.deleStatus='-') and surm.moduleId=o.id and surm.roleId=? ");
		sb.append(" order by o.orderNum,o.parentId");
		return super.getJdbcTemplate().query(sb.toString(),new Object[]{roleId},new RowMapper<Map<String,String>>(){
			public Map<String, String> mapRow(ResultSet arg0, int arg1) throws SQLException {
				Map<String,String> map=Maps.newHashMap();
				map.put("name",arg0.getString("name"));
				map.put("securityName",arg0.getString("securityName"));
				map.put("resourceValue",arg0.getString("resourceValue"));
				map.put("orderNum",arg0.getString("orderNum"));
				map.put("parentId",arg0.getString("parentId"));
				map.put("id",arg0.getString("id"));
				map.put("deleStatus",arg0.getString("deleStatus"));
				return map;
			}
		});
	}

	/**
	 * findByRoleIdModuleId(查询模块权限)
	 * @param roleId 角色
	 * @param moduleId 模块id
	 * @return
	 */
	public String findByRoleIdModuleId(long roleId, long moduleId) {
		StringBuffer sb=new StringBuffer("select o.securityName as securityName from sys_user_module o,sys_user_role_module_r surmr where surmr.moduleId=o.id and (o.deleStatus='1' or o.deleStatus='-') and surmr.roleId=? and surmr.moduleId=? ");
		return super.getJdbcTemplate().queryForObject(sb.toString(),new Object[]{roleId,moduleId},new RowMapper<String>(){
			public String mapRow(ResultSet arg0, int arg1) throws SQLException {
				return arg0.getString("securityName");
			}
		});
	} 
}

