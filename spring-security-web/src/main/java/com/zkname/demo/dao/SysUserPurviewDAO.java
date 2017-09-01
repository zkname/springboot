package com.zkname.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zkname.core.dao.BaseDAO;
import com.zkname.demo.entity.SysUserPurview;

@Repository
public class SysUserPurviewDAO extends BaseDAO<SysUserPurview> {

    
	/**
	 * 用户id查询
	 * @param userId
	 * @return
	 */
	public SysUserPurview getSysUserPurview(long userId) {
		String sql="select o.*  from sys_user_purview o where o.userId=?";
		return this.findBy(sql,userId);
	}
	
}

