package com.zkname.hd.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.frame.dao.IBaseDAO;
import com.zkname.frame.service.BaseService;
import com.zkname.frame.util.memcache.XMemcachedClientImp;
import com.zkname.hd.dao.SysUserDAO;
import com.zkname.hd.entity.SysUser;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysUserService extends BaseService<SysUser> {
	
	@Autowired
	private SysUserDAO dao;
	
	@Resource(name = "baseXMemcachedClient")
	private XMemcachedClientImp baseXMemcachedClient;

	@Transactional(readOnly=true)//非事务处理
	public IBaseDAO<SysUser> getDAO() {
		return dao;
	}

	/**
	 * 
	 * findUserByUserName(查询用户是否已存在)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param userName
	 * @return 
	 * SysUser
	 * @exception 
	 * @since  1.0.0
	 */
	@Transactional(readOnly=true)//非事务处理
	public SysUser findUserByUserName(String userName) {
		return dao.findUserByUserName(userName);
	}
	
	/**
	 * 判断用户名是否可以注册
	 * @param oldUserName
	 * @param newUserName
	 * @return
	 */
	@Transactional(readOnly=true)//非事务处理
	public boolean checkLoginName(String oldLoginName, String userName) {
		if (userName.equals(oldLoginName)) {
			return true;
		} else if (this.findUserByUserName(userName) != null) {
			return false;
		}
		return true;
	}
}