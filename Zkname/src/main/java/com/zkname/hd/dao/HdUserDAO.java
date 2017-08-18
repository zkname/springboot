package com.zkname.hd.dao;

import org.springframework.stereotype.Repository;

import com.zkname.frame.dao.BaseDAO;
import com.zkname.hd.entity.HdUser;

@Repository
public class HdUserDAO extends BaseDAO<HdUser> {

	public void updateStatus(Long id, int status) {
		String sql = "update hd_user o set o.deleStatus=?,o.updateTime=now() where o.id=? and o.deleStatus!=?";
		super.update(sql, status, id, status);
	}
	
	/**
	 * unionid 查询用户
	 * @param unionid
	 * @return
	 */
	public HdUser findByOpenid(String openid){
		String sql = "select * from hd_user o where o.openId=?";
		return super.findBy(sql, openid);
	}
}