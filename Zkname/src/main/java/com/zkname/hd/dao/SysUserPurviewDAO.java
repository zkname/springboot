package com.zkname.hd.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zkname.frame.dao.BaseDAO;
import com.zkname.hd.entity.SysUserPurview;

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

