package com.zkname.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zkname.core.dao.BaseDAO;
import com.zkname.demo.entity.SysUserRole;

@Repository
public class SysUserRoleDAO extends BaseDAO<SysUserRole> {

	//查询平台下的所有角色
	public List<SysUserRole> findAll(long platformId) {
		StringBuffer sb=new StringBuffer("select o.*  from sys_user_role o,sys_platform sp where sp.id=o.platformId ");
		if(platformId!=1){
			sb.append(" and o.platformId=?");
			return this.find(sb.toString(),platformId);
		}
		return this.find(sb.toString());
	}
	
	
    //查询平台下的所有角色
    public List<SysUserRole> findAll(long platformId,long notId) {
        StringBuffer sb=new StringBuffer("select o.*  from sys_user_role o  where ");
        sb.append("  o.id!=").append(notId);
//      if(platformId!=1){
//          sb.append(" and o.platformId=?");
//          return this.getDbJobFactory().getDbJob().find(new AutoBoxingRowMapper<SysUserRole>(SysUserRole.class),sb.toString(),platformId);
//      }
        sb.append(" order by o.roleCode ");
        return this.find(sb.toString());
    }
    
    //查询平台下的所有角色
    public List<SysUserRole> findAll(long platformId,String notCode) {
        StringBuffer sb=new StringBuffer("select o.*  from sys_user_role o  where ");
        sb.append("  o.roleCode not like '").append(notCode).append("%' ");
//      if(platformId!=1){
//          sb.append(" and o.platformId=?");
//          return this.getDbJobFactory().getDbJob().find(new AutoBoxingRowMapper<SysUserRole>(SysUserRole.class),sb.toString(),platformId);
//      }
        sb.append(" order by o.roleCode ");
        return this.find(sb.toString());
    }

    //查询平台下的所有角色
    public List<SysUserRole> findLike(long platformId,String code) {
        StringBuffer sb=new StringBuffer("select o.*  from sys_user_role o  where o.deleStatus='1' and ");
        sb.append("  o.roleCode != '").append(code).append("' and o.roleCode like '").append(code).append("%' ");
        sb.append(" order by o.roleCode ");
        return this.find(sb.toString());
    }
	
	public int getCountRole(long id,String name,long platformId){
		StringBuffer sb=new StringBuffer("select count(*) from sys_user_role o where o.id!=? and o.name=? and o.platformId=? ");
		return this.queryCount(sb.toString(),id,name,platformId);
	}
	
	/**
	 * 
	 * findByName(查询用户是否已存在)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param name
	 * @return 
	 * SysUserRole
	 * @exception 
	 * @since  1.0.0
	 */
	public SysUserRole findByName(long platformId,String name){
		StringBuffer sb=new StringBuffer("select o.* from sys_user_role o where o.name=? and o.platformId=? ");
		return this.findBy(sb.toString(), name,platformId);
	}

	
	/**
	 * getNewCode(查询父节点的最小一个)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param parentId
	 * @return 
	 * SysUserRole
	 * @exception 
	 * @since  1.0.0
	 */
	public SysUserRole getNewCode(long parentId) {
		StringBuffer sb=new StringBuffer("select o.* from sys_user_role o where o.parentId=? order by o.roleCode desc limit 1");
		return this.findBy(sb.toString(), parentId);
	}

	/**
	 * getCountRoleCode(查询子角色数量)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param roleCode
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	public int getCountRoleCode(String roleCode) {
		StringBuffer sb=new StringBuffer("select count(*) from sys_user_role o where o.roleCode!=? and o.roleCode like ? ");
		return this.queryCount(sb.toString(),roleCode,roleCode+"%");
	}

    /**
     * 查询子角色
     */
    public List<SysUserRole> getSon(long parentId) {
        StringBuffer sb=new StringBuffer("select o.* from sys_user_role o where o.parentId=? order by o.roleCode");
        return this.find(sb.toString(), parentId);
    }
    
    
    /**
     * 修改用户code
     * @param oldCodeValue
     * @param newCodeValue
     */
    public void updateCode(String oldCodeValue,String newCodeValue){
        this.update("update sys_user_role set roleCode=concat(?,substring(roleCode, ?)) where roleCode like ?",newCodeValue,oldCodeValue.length()+1,oldCodeValue+"%");
    }
}

