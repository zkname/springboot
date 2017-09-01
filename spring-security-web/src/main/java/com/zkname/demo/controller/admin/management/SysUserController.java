package com.zkname.demo.controller.admin.management;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.zkname.core.controller.BaseController;
import com.zkname.core.util.exception.ActionException;
import com.zkname.demo.entity.SysPlatform;
import com.zkname.demo.entity.SysUser;
import com.zkname.demo.page.PageadminSysUser;
import com.zkname.demo.security.LoginUser;
import com.zkname.demo.security.springsecurity.SpringSecurityUtils;
import com.zkname.demo.service.SysPlatformService;
import com.zkname.demo.service.SysUserRoleService;
import com.zkname.demo.service.SysUserService;
import com.zkname.demo.service.SysUserUserRoleRService;

@Controller
@RequestMapping(value = "/admin/management/sysuser")
public class SysUserController extends BaseController{
	
	// 用户业务层类
	@Autowired
	private SysUserService service;
	
	@Autowired
	private SysUserRoleService isysSysUserRoleService;
	
	@Autowired
	private SysUserUserRoleRService  isysSysUserUserRoleRService;
	
	@Autowired
	private SysPlatformService iadminSysPlatformService;
	
    //模块id
    public static final Integer MODLE_ID = 2;
	
	/**
	 * list(列表)
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
	    PageadminSysUser page=(PageadminSysUser) request.getSession().getAttribute("SysUserController");
	    if(page==null || request.getParameterMap().containsKey("sessionRemove")){
	        page=new PageadminSysUser();
	        request.getSession().setAttribute("SysUserController", page);
	    }
	    page.setHttpServletRequestValue(request);
	    if(page.getRoleCode()==null || "".equals(page.getRoleCode())){
	    	page.setRoleCode(SpringSecurityUtils.getLoginUser().getRoleCode());
	    }
	    page.setId(SpringSecurityUtils.getLoginUser().getId());
		page.query();
		
        ModelAndView mv = new ModelAndView("admin/management/sysuser/list");
        mv.addObject("page", page);
        
        //查询角色
        mv.addObject("roleList",isysSysUserRoleService.findLike(SysPlatform.平台,SpringSecurityUtils.getLoginUser().getRoleCode()));
        
		return mv;
	}
	
	/**
	 * add(添加页面)
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("admin/management/sysuser/add");
        //查询角色
        mv.addObject("roleList",isysSysUserRoleService.findLike(SysPlatform.平台,SpringSecurityUtils.getLoginUser().getRoleCode()));
		return mv;
	}
	
	/**
	 * addInput(添加提交功能)
	 */
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	public RedirectView add(SysUser entity,Long roleId,HttpServletRequest request,HttpServletResponse response) {
	    
	    if (service.findUserByUserName(entity.getUserName()) != null) {
            throw new ActionException("用户名已存在!");
        }
	    
		LoginUser user=SpringSecurityUtils.getCurrentUser();
		entity.setCreateTime(new Date());
		entity.setPassword(DigestUtils.md5Hex(entity.getPassword()));
		entity.setCreatorId(Long.valueOf(user.getId()));
		entity.setDeleStatus("1");
		entity.setUpdateTime(new Date());
		entity.setPlatformId(Long.valueOf(SysPlatform.平台));
        
        String [] producersIds = request.getParameterValues("producersId");
		service.save(roleId,producersIds,entity);
		RedirectView mv = new RedirectView("list.do");
		return mv;
	}
	
	
	/**
	 * update(修改页面)
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable int id,HttpServletRequest request,HttpServletResponse response) {
		SysUser entity = service.findById(id);
        ModelAndView mv = new ModelAndView("admin/management/sysuser/update");
        //查询角色
        int platformId=SysPlatform.平台;
        mv.addObject("roleList",isysSysUserRoleService.findLike(SysPlatform.平台,SpringSecurityUtils.getLoginUser().getRoleCode()));
        mv.addObject("roleSelectList",isysSysUserUserRoleRService.findUserAll(id,platformId));
        
        mv.addObject("entity", entity);
		return mv;
	}
	
	/**
	 * updateInput(修改提交功能)
	 */
	@RequestMapping(value = "/updateInput", method = RequestMethod.POST)
	public RedirectView updateInput(SysUser entity,int id,Long roleId,String [] producersId,HttpServletRequest request,HttpServletResponse response) {
		SysUser entityUpdate = service.findById(id);
		entity.setPlatformId(Long.valueOf(SysPlatform.平台));
		//获取参数实体，操作更新实体，不拷贝字段
		if(entity.getPassword()==null || "".equals(entity.getPassword())){
			BeanUtils.copyProperties(entity,entityUpdate,new String[]{"id","password","createTime","updateTime","deleStatus","loginTime","creatorId","sysUserUserRoleRs","roleCode","sysUserPurviews","userInfos"});
		}else{
			BeanUtils.copyProperties(entity,entityUpdate,new String[]{"id","createTime","updateTime","deleStatus","loginTime","creatorId","sysUserUserRoleRs","roleCode","sysUserPurviews","userInfos"});
			entityUpdate.setPassword(DigestUtils.md5Hex(entity.getPassword()));
		}
		entityUpdate.setUpdateTime(new Date());
        //查询角色
        int platformId=SysPlatform.平台;
		service.update(roleId,platformId,producersId,entityUpdate);
		RedirectView mv = new RedirectView("list.do");
		return mv;
	}
	
	/**
	 * delete(废止功能)
	 */
	@RequestMapping(value = "/delete")
	public RedirectView delete(Long [] ids,String type,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list.do");
		if(service.updateStatus(type,ids)>0){
			return mv;
		}else{
			throw new ActionException("删除失败！");
		}
	}
	
	

	/**
	 * checkLoginName(查询用户是否已经存在)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param oldLoginName
	 * @param username
	 * @param request
	 * @param response
	 * @return 
	 * String
	 * @throws UnsupportedEncodingException 
	 * @exception 
	 * @since  1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/checkLoginName", produces = "application/text; charset=utf-8")
	public String checkLoginName(@RequestParam("oldLoginName") String oldLoginName,@RequestParam("userName") String userName,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		if (userName.equals(oldLoginName)) {
			return null;
		} else if (service.findUserByUserName(userName) != null) {
			return "用户名已存在";
		}
		return null;
	}
    
    
}
