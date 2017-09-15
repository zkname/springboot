package com.zkname.credit.card.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.credit.card.dao.SysUserDAO;
import com.zkname.credit.card.entity.CinvitationCode;
import com.zkname.credit.card.entity.SysUser;
import com.zkname.credit.card.session.LoginUser;
import com.zkname.core.dao.IBaseDAO;
import com.zkname.core.service.BaseService;
import com.zkname.core.util.exception.ActionException;
import com.zkname.core.util.exception.DaoException;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysUserService extends BaseService<SysUser> {
	
	@Autowired
	private SysUserDAO dao;

	@Autowired
	private CinvitationCodeService cinvitationCodeService;
	
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
	public SysUser login(String userName) {
		return dao.login(userName);
	}
	
	/**
	 * 更新登陆时间
	 * @param id
	 * @return
	 */
	public void updateLoginTime(SysUser user) {
		dao.update(user, "loginTime");
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
	public SysUser findUserByEmail(String email) {
		return dao.findUserByEmail(email);
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
	
	/**
	 * 注册
	 * @param sysUser
	 * @param cinvitationCode
	 */
	public void register(SysUser sysUser,CinvitationCode cinvitationCode,int OPEN_REGISTER){
		this.save(sysUser);
		if(OPEN_REGISTER==0){
			cinvitationCode.setUserId(sysUser.getId());
			cinvitationCode.setUpdateTime(new Date());
			
			if(cinvitationCodeService.register(cinvitationCode)<1){
				throw new ActionException("请勿重复注册！");
			};
		}
	}
}