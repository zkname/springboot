package com.zkname.hd.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.zkname.frame.dao.IBaseDAO;
import com.zkname.frame.service.BaseService;
import com.zkname.frame.util.memcache.XMemcachedClientImp;
import com.zkname.hd.dao.SysUserDAO;
import com.zkname.hd.entity.SysUser;
import com.zkname.hd.entity.SysUserPurview;
import com.zkname.hd.entity.SysUserRole;
import com.zkname.hd.entity.SysUserUserRoleR;
import com.zkname.hd.util.exception.DaoException;
import com.zkname.hd.vo.SysUserVo;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysUserService extends BaseService<SysUser> {
	
	@Autowired
	private SysUserDAO dao;
	
	@Autowired
	private SysUserModuleService iadminSysUserModuleService;
	
	@Autowired
	private SysUserUserRoleRService isysSysUserUserRoleRService;

	@Autowired
	private SysUserRoleService iadminSysUserRoleService;
	
	@Autowired
	private SysUserPurviewService iadminSysUserPurviewService;
	
	@Resource(name = "baseXMemcachedClient")
	private XMemcachedClientImp baseXMemcachedClient;

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
	public SysUserVo login(String userName,Long platformId) {
		SysUser sysUserDto=dao.login(userName);
		if(sysUserDto!=null){
			SysUserVo suv=new SysUserVo();
			//获取参数实体，操作更新实体，不拷贝字段
			BeanUtils.copyProperties(sysUserDto,suv);
			//获取用户权限 特殊角色获取
			if(SysUserRoleService.ROLE_CODE_SUBACCOUNT.equals(sysUserDto.getRoleCode())){
			    SysUserPurview sup=iadminSysUserPurviewService.getSysUserPurview(sysUserDto.getId());
				suv.setUserPurviewTemplate(sup.getPurviews());
			}else{
				suv.setUserPurviewTemplate(iadminSysUserModuleService.getUserPurviewTemplate(platformId, sysUserDto.getId()));
			}
			return suv;
		}
		return null;
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
	public SysUserVo login(Long userId,Long platformId) {
		SysUser sysUserDto=dao.findById(userId);
		if(sysUserDto!=null){
			SysUserVo suv=new SysUserVo();
			//获取参数实体，操作更新实体，不拷贝字段
			BeanUtils.copyProperties(sysUserDto,suv);
			//获取用户权限 特殊角色获取
			if(SysUserRoleService.ROLE_CODE_SUBACCOUNT.equals(sysUserDto.getRoleCode())){
			    SysUserPurview sup=iadminSysUserPurviewService.getSysUserPurview(sysUserDto.getId());
				suv.setUserPurviewTemplate(sup.getPurviews());
			}else{
				suv.setUserPurviewTemplate(iadminSysUserModuleService.getUserPurviewTemplate(platformId, sysUserDto.getId()));
			}
			return suv;
		}
		return null;
	}
	
	/**
	 * loginSuccess(用户登陆成功修改数据)
	 * @param userId 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void loginSuccess(Long userId,Long platformId) {
		SysUser sysUser=dao.findById(userId);
		if(sysUser!=null){
			sysUser.setLoginTime(new Date());
			this.saveOrUpdate(sysUser);
		}
	}
	
	
	/**
	 * 新增
	 */
	public void save(Long roleId,String [] producersIds,SysUser entity) {
		if(dao.getCountRegistration(entity.getId()==null?0:entity.getId(),entity.getUserName())>0){
			throw new DaoException("用户名已存在！");
		}
		
		SysUserRole sysUserRole=iadminSysUserRoleService.findById(roleId);
		entity.setRoleCode(sysUserRole.getRoleCode());
//		entity.setPlatformId(sysUserRole.getPlatformId());
		
		this.saveOrUpdate(entity);
		//添加权限
//		if(roleId==null &&roleId!=0)return;
//		Set<Integer> strSet = Sets.newHashSet();    
//		CollectionUtils.addAll(strSet, roleId);  
//		for(Integer obj :strSet){
//			if(obj==null || obj.intValue()==0)continue;
		SysUserUserRoleR suurr=new SysUserUserRoleR();
		suurr.setRoleId(roleId);
		suurr.setUserId(entity.getId());
		isysSysUserUserRoleRService.saveOrUpdate(suurr);
//		}
	}

	public void update(Long roleId, long platformId,String [] producersIds, SysUser entity) {
		
		if(dao.getCountRegistration(entity.getId(),entity.getUserName())>0){
			throw new DaoException("用户名已存在！");
		}
		
		
		SysUserRole sysUserRole=iadminSysUserRoleService.findById(roleId);
		entity.setRoleCode(sysUserRole.getRoleCode());
//		entity.setPlatformId(sysUserRole.getPlatformId());
		
		this.saveOrUpdate(entity);
//		Set<Integer> strSet = Sets.newHashSet(); 
//		if(roleId!=null ) 
//			CollectionUtils.addAll(strSet, roleId);
		
		
		List<SysUserUserRoleR> list=isysSysUserUserRoleRService.findUserAll(entity.getId(),platformId);
		
		for(int i=0;i<list.size();i++){
			SysUserUserRoleR sysUserUserRoleR=list.get(i);
			if(roleId!=null && roleId.equals(sysUserUserRoleR.getRoleId())){
//				strSet.remove(sysUserUserRoleR.getRoleId());
				list.remove(i);
				roleId=null;
				break;
			}
		}
		//删除为选择的权限
		for(int i=0;i<list.size();i++){
			SysUserUserRoleR sysUserUserRoleR=list.get(i);
			isysSysUserUserRoleRService.deleteId(sysUserUserRoleR.getId());
		}
		
		if(roleId!=null && roleId.intValue()!=0){
			SysUserUserRoleR suurr=new SysUserUserRoleR();
			suurr.setRoleId(roleId);
			suurr.setUserId(entity.getId());
			isysSysUserUserRoleRService.saveOrUpdate(suurr);
		}
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


    public void updateCode(String oldCodeValue, String newCodeValue) {
        dao.updateCode(oldCodeValue,newCodeValue);
    }

    @Transactional(readOnly=true)//非事务处理
	public List<String> listAllUserName() {
		return dao.listAllUserName();
	}
	
	
	/**
	 * 开发者注册
	 */
	public void saveDevelopers(SysUser entity) {
		if(dao.getCountRegistration(entity.getId()==null?0:entity.getId(),entity.getUserName())>0){
			throw new DaoException("用户名已存在！");
		}
		//开发者 101	开发者	10001000
		entity.setRoleCode(SysUserRoleService.ROLE_CODE_DEVELOPERS);
		this.saveOrUpdate(entity);
//		userInfo.setUserId(entity.getId());
//		iadminUserInfoService.saveOrUpdate(userInfo);
		
		//添加权限
		SysUserUserRoleR suurr=new SysUserUserRoleR();
		suurr.setRoleId(SysUserRoleService.ROLE_CODE_DEVELOPERS_ID);
		suurr.setUserId(entity.getId());
		isysSysUserUserRoleRService.saveOrUpdate(suurr);
	}
	
	/**
	 * 子账户注册
	 */
	public void saveSubaccount(SysUser entity,String [] purview,Long [] appId) {
		if(dao.getCountRegistration(entity.getId()==null?0:entity.getId(),entity.getUserName())>0){
			throw new DaoException("用户名已存在！");
		}
		//开发者 102	子账户	100010001000
		entity.setRoleCode(SysUserRoleService.ROLE_CODE_SUBACCOUNT);
		this.saveOrUpdate(entity);
		
//		userInfo.setUserId(entity.getId());
//		iadminUserInfoService.saveOrUpdate(userInfo);
		
		
//		Set<Long> set =	Sets.newHashSet(iadminUserAppLinksService.findByUserId(entity.getId()));
//		for(Long aid:appId){
//			if(aid==null || aid.longValue()==0l){
//				continue;
//			}
//			UserAppLinks ual=new UserAppLinks();
//			ual.setAppId(aid);
//			ual.setUserId(entity.getId());
//			if(set.contains(aid)){
//				set.remove(aid);
//				continue;
//			}
//			iadminUserAppLinksService.saveOrUpdate(ual);
//		}
//		
//		iadminUserAppLinksService.deleteSet(entity.getId(),set);
		
		
		//添加权限
		SysUserUserRoleR suurr=isysSysUserUserRoleRService.getSysUserUserRoleR(SysUserRoleService.ROLE_CODE_SUBACCOUNT_ID,entity.getId());
		if(suurr==null){
			suurr=new SysUserUserRoleR();
			suurr.setRoleId(SysUserRoleService.ROLE_CODE_SUBACCOUNT_ID);
			suurr.setUserId(entity.getId());
			isysSysUserUserRoleRService.saveOrUpdate(suurr);
		}
		String purviewText=getPurviewText(purview);
		SysUserPurview sup=iadminSysUserPurviewService.getSysUserPurview(entity.getId());
		if(sup==null){
			sup=new SysUserPurview();
			sup.setUserId(entity.getId());
		}
		sup.setPurviewText(purviewText);
		iadminSysUserPurviewService.saveOrUpdate(sup);
		
		
		
		
	}
	
	private String getPurviewText(String [] purview){
		StringBuffer sb=new StringBuffer();
		Set<String> strSet = null;    
		//添加模块权限
		if(purview!=null && purview.length>0){
			strSet = Sets.newHashSet();   
			CollectionUtils.addAll(strSet, purview);  
			for(String obj :strSet){
				if(obj!=null){
					sb.append(obj).append(",");
				}
			}
		}
		return sb.toString();
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