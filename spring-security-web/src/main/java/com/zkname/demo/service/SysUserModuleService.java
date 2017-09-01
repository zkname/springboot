package com.zkname.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.zkname.demo.dao.SysUserModuleDAO;
import com.zkname.demo.entity.SysUserModule;
import com.zkname.demo.entity.SysUserModulePermission;
import com.zkname.demo.vo.MenuVo;
import com.zkname.demo.vo.PurviewVo;
import com.zkname.core.dao.IBaseDAO;
import com.zkname.core.service.BaseService;
import com.zkname.core.util.ParamType;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysUserModuleService extends BaseService<SysUserModule> {
	
	@Autowired
	private SysUserModuleDAO dao;
	
	@Autowired
	private SysUserModulePermissionService sysUserModulePermissionService;
	
	@Transactional(readOnly=true)//非事务处理
	public IBaseDAO<SysUserModule> getDAO() {
		return dao;
	}
	
	@Transactional(readOnly=true)//非事务处理
	public MenuVo getPurviewMenuTemplate() {
		return this.getMenuTemplate(1);
	}
	
	/**
	 * getPurviewTemplate(模块权限获取)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param platformId
	 * @return 
	 * Map<String,Collection<ConfigAttribute>>
	 * @exception 
	 * @since  1.0.0
	 */
	@Transactional(readOnly=true)//非事务处理
	public Map<String, Collection<String>> getPurviewTemplate(long platformId) {
		Map<String, Collection<String>> resourceMap=Maps.newHashMap();
		//获取模块权限
		List<Map<String,String>> modules=this.dao.findAll(platformId);
		for(Map<String,String> map:modules){
			Collection<String> c = null;
			if(resourceMap.containsKey(map.get("resourceValue"))){
				c=resourceMap.get(map.get("resourceValue"));
			}else{
				c=new ArrayList<String>();
			}
			c.add("A_"+map.get("securityName"));
			resourceMap.put(map.get("resourceValue"), c);
		}
		//获取模块功能权限
		List<Map<String,String>> permissions=this.dao.findPermissionAll(platformId);
		for(Map<String,String> map:permissions){
			Collection<String> c = null;
			if(resourceMap.containsKey(map.get("resourceValue"))){
				c=resourceMap.get(map.get("resourceValue"));
			}else{
				c=new ArrayList<String>();
			}
			c.add("A_"+map.get("securityName"));
			resourceMap.put(map.get("resourceValue"), c);
		}
		return resourceMap;
	}
	
	/**
	 * getMenuTemplate(获取菜单)
	 * @param platformId
	 * @return 
	 * MenuVo
	 * @exception 
	 * @since  1.0.0
	 */
	@Transactional(readOnly=true)//非事务处理
	public MenuVo getMenuTemplate(long platformId){
		MenuVo mv=new MenuVo();
		mv.setId(0);
		mv.setName("后台管理");
		//获取模块权限
		List<Map<String,String>> modules=this.dao.findMenuAll(platformId);
		orderMenu(mv, modules);
		return mv;
	}
	
	private void orderMenu(MenuVo mv,List<Map<String,String>> modules){
		String parentId=Integer.toString(mv.getId());
		for(int i=0;i<modules.size();i++){
			Map<String,String> map=modules.get(i);
			if(parentId.equals(map.get("parentId"))){
				MenuVo mvSun=new MenuVo();
				mvSun.setId(ParamType.getint(map.get("id")));
				mvSun.setName(map.get("name"));
				mvSun.setSecurityName("A_"+map.get("securityName"));
				mvSun.setParentId(mv.getId());
				mvSun.setUrl(map.get("resourceValue"));
				mvSun.setDeleStatus(map.get("deleStatus"));
				
				if(mvSun.getUrl()!=null){
					String url=mvSun.getUrl().replaceAll("\\*\\*", "");
					if(url.lastIndexOf("/")==url.length()-1){
						mvSun.setUrl(url+"list");
					}else{
						mvSun.setUrl(url);
					}
				}
				mv.getLists().add(mvSun);
//				modules.remove(i);
//				i--;
				orderMenu(mvSun, modules);
			}
		}
	}
	
	
	
	/**
	 * getUserPurviewTemplate(用户模块权限获取)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param platformId
	 * @return 
	 * Map<String,GrantedAuthority>
	 * @exception 
	 * @since  1.0.0
	 */
	@Transactional(readOnly=true)//非事务处理
	public HashMap<String, String> getUserPurviewTemplate(long platformId,long userId){
		HashMap<String, String> resourceMap=Maps.newHashMap();
		//获取模块权限
		List<String> modules=this.dao.findUserAll(userId,platformId);
		for(String key:modules){
			String value="A_"+key;
			if(!resourceMap.containsKey(value)){
				resourceMap.put(value, value);
			}
		}
		//获取模块功能权限
		List<String> permissions=this.dao.findUserPermissionAll(userId,platformId);
		for(String key:permissions){
			String value="A_"+key;
			if(!resourceMap.containsKey(value)){
				resourceMap.put(value, value);
			}
		}
		return resourceMap;
	}

	
	@Transactional(readOnly=true)//非事务处理
	public MenuVo getPurviewAndMenu(long platformId) {
		MenuVo mv=new MenuVo();
		mv.setId(0);
		mv.setName("后台管理");
		//获取模块权限
		List<Map<String,String>> modules=this.dao.findMenuAll(platformId);
		
		List<SysUserModulePermission> list=sysUserModulePermissionService.findAll(platformId);
		orderPurviewAndMenu(mv, modules,list);
		
		return mv;
	}
	
	private void orderPurviewAndMenu(MenuVo mv,List<Map<String,String>> modules,List<SysUserModulePermission> list){
		String parentId=Integer.toString(mv.getId());
		for(int i=0;i<modules.size();i++){
			Map<String,String> map=modules.get(i);
			if(parentId.equals(map.get("parentId"))){
				MenuVo mvSun=new MenuVo();
				mvSun.setId(ParamType.getint(map.get("id")));
				mvSun.setName(map.get("name"));
				mvSun.setSecurityName("A_"+map.get("securityName"));
				mvSun.setParentId(mv.getId());
				mvSun.setUrl(map.get("resourceValue"));
				if(mvSun.getUrl()!=null){
					mvSun.setUrl(mvSun.getUrl().replaceAll("\\*\\*", ""));
				}
				mv.getLists().add(mvSun);
				for(int j=0;j<list.size();j++){
					SysUserModulePermission sup=list.get(j);
					if(sup.getModuleId()==mvSun.getId()){
						mvSun.getPurviewLists().add(new PurviewVo(sup.getId(),sup.getName(),sup.getModuleId(),sup.getSecurityName(),sup.getResourceValue()));
						list.remove(j);
						j--;
					}

				}
//				modules.remove(i);
//				i--;
				orderPurviewAndMenu(mvSun, modules,list);
			}
		}
	}
	
	
	
	
	
	@Transactional(readOnly=true)//非事务处理
	public MenuVo getRoleIdPurviewAndMenu(int roleId) {
		MenuVo mv=new MenuVo();
		mv.setId(0);
		//获取模块权限
		List<Map<String,String>> modules=this.dao.findRoleIdMenuAll(roleId);
		
		List<SysUserModulePermission> list=sysUserModulePermissionService.findRoleIdAll(roleId);
		orderPurviewAndMenu(mv, modules,list);
		
		return mv;
	}
}