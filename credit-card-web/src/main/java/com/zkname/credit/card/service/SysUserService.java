package com.zkname.credit.card.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.credit.card.dao.SysUserDAO;
import com.zkname.credit.card.entity.SysUser;
import com.zkname.credit.card.session.LoginUser;
import com.zkname.core.dao.IBaseDAO;
import com.zkname.core.service.BaseService;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysUserService extends BaseService<SysUser> {
	
	@Autowired
	private SysUserDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public IBaseDAO<SysUser> getDAO() {
		return dao;
	}
	
	/**
	 * login(用户登陆)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param userName
	 * @param passWord
	 * @return 
	 * SysUserDto
	 * @exception 
	 * @since  1.0.0
	 */
	@Transactional(readOnly=true)//非事务处理
	public LoginUser login(String userName) {
		SysUser sysUserDto=dao.login(userName);
		if(sysUserDto!=null){
			LoginUser suv=new LoginUser();
			suv.setId(sysUserDto.getId());
			suv.setPassword(sysUserDto.getPassword());
			suv.setUsername(sysUserDto.getUsername());
			suv.setEmail(sysUserDto.getEmail());
			return suv;
		}
		return null;
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
	 * updateStatus(修改状态)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param id
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	public int updateStatus(String type,Long... ids) {
		if(ids==null)return 0;
		int i=0;
		for(Long id:ids){
			SysUser sysUser=dao.findById(id);
			if(sysUser==null ||sysUser.getId()==1){
				continue;
			}
			sysUser.setDeleStatus(type);
			dao.saveOrUpdate(sysUser);
			i++;
		}
		return i;
	}
	
	
	/**
	 * updateLock(修改状态)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param id
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	public int updateLock(Long userId,String type,Long... ids) {
		if(ids==null)return 0;
		int i=0;
		for(Long id:ids){
			SysUser sysUser=dao.findById(id);
			if(sysUser==null ||sysUser.getId()==1 || sysUser.getCreatorId().longValue()!=userId.longValue()){
				continue;
			}
			sysUser.setDeleStatus(type);
			sysUser.setUpdateTime(new Date());
			dao.saveOrUpdate(sysUser);
			i++;
		}
		return i;
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