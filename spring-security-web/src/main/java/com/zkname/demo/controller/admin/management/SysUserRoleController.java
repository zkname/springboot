package com.zkname.demo.controller.admin.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.zkname.core.util.ParamType;
import com.zkname.core.util.exception.ActionException;
import com.zkname.demo.entity.SysPlatform;
import com.zkname.demo.entity.SysUserRole;
import com.zkname.demo.page.PageadminSysUserRole;
import com.zkname.demo.security.springsecurity.SpringSecurityUtils;
import com.zkname.demo.service.SysPlatformService;
import com.zkname.demo.service.SysUserModuleService;
import com.zkname.demo.service.SysUserRoleModulePermissionRService;
import com.zkname.demo.service.SysUserRoleModuleRService;
import com.zkname.demo.service.SysUserRoleService;
import com.zkname.demo.vo.MenuVo;

@Controller
@RequestMapping(value = "/admin/management/sysuserrole")
public class SysUserRoleController extends BaseController{
	
	// 用户业务层类 
	@Autowired
	private SysUserRoleService service;
	
	@Autowired
	private SysUserRoleModuleRService isysSysUserRoleModuleRService;
	
	@Autowired
	private SysUserRoleModulePermissionRService isysSysUserRoleModulePermissionRService;
	
	@Autowired
	private SysUserModuleService iadminSysUserModuleService;
	
	@Autowired
	private SysPlatformService iadminSysPlatformService;
	
	/**
	 * list(列表)
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
	    
	    PageadminSysUserRole page=(PageadminSysUserRole) request.getSession().getAttribute("SysUserRoleController");
        if(page==null || request.getParameterMap().containsKey("sessionRemove")){
            page=new PageadminSysUserRole();
            request.getSession().setAttribute("SysUserRoleController", page);
        }
        page.setHttpServletRequestValue(request);
	    
		page.query();
        ModelAndView mv = new ModelAndView("admin/management/sysuserrole/list");
        mv.addObject("page", page);
        //所属机构
        mv.addObject("sysPlatformList",iadminSysPlatformService.findAll());
		return mv;
	}
	
	/**
	 * add(添加页面)
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("admin/management/sysuserrole/add");
        int platformId=SysPlatform.平台;
		MenuVo purviewMenuTemplate=iadminSysUserModuleService.getPurviewAndMenu(platformId);;
        mv.addObject("menuVoList",purviewMenuTemplate.getLists());
        mv.addObject("sysUserRoleList",  service.findAll(platformId,0));
        //所属机构
//        mv.addObject("sysPlatformList",iadminSysPlatformService.getDAO().findAll());
		return mv;
	}
	
	/**
	 * addInput(添加提交功能)
	 */
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	public RedirectView add(SysUserRole entity,Long[]moduleId,Long [] purviewId,HttpServletRequest request,HttpServletResponse response) {
		entity.setCreateTime(new Date());
		entity.setCreatorId(Long.valueOf(SpringSecurityUtils.getLoginUser().getId()));
		entity.setDeleStatus("1");
		entity.setPlatformId(1l);
		entity.setUpdateTime(new Date());
		service.save(moduleId,purviewId,entity);
		RedirectView mv = new RedirectView("list.do");
		return mv;
	}
	
	
	/**
	 * update(修改页面)
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable int id,HttpServletRequest request,HttpServletResponse response) {
		SysUserRole entity = service.findById(id);
        ModelAndView mv = new ModelAndView("admin/management/sysuserrole/update");
        //查询平台
        int platformId=1;
		MenuVo purviewMenuTemplate=iadminSysUserModuleService.getPurviewAndMenu(platformId);	
        
        mv.addObject("menuVoList",purviewMenuTemplate.getLists());
        mv.addObject("menuVoSelectList",isysSysUserRoleModuleRService.findRoleAll(id,platformId));
        mv.addObject("menuVoPermissionSelectList",isysSysUserRoleModulePermissionRService.findRoleAll(id,platformId));
        mv.addObject("entity", entity);
        mv.addObject("sysUserRoleList",  service.findAll(platformId,entity.getRoleCode()));
        //所属机构
//        mv.addObject("sysPlatformList",iadminSysPlatformService.getDAO().findAll());
		return mv;
	}
	
	/**
	 * updateInput(修改提交功能)
	 */
	@RequestMapping(value = "/updateInput", method = RequestMethod.POST)
	public RedirectView updateInput(SysUserRole entity,int id,Long[]moduleId,Long [] purviewId,HttpServletRequest request,HttpServletResponse response) {
		SysUserRole entityUpdate = service.findById(id);
		
		//获取参数实体，操作更新实体，不拷贝字段
		BeanUtils.copyProperties(entity,entityUpdate,new String[]{"id","createTime","updateTime","deleStatus","creatorId","roleCode","parentId","platformId"});
	
		String parentId=request.getParameter("parentId");
		long pId=-1;
		if(parentId!=null && !parentId.trim().equals("")){
		    pId=ParamType.getint(parentId);
		}
		entityUpdate.setUpdateTime(new Date());
		service.update(moduleId,purviewId,entityUpdate,pId);
		
		RedirectView mv = new RedirectView("list.do");
		return mv;
	}
	
	
	/**
	 * delete(删除功能)
	 */
	@RequestMapping(value = "/deleStatus")
	public RedirectView deleStatus(Long[] ids,int type,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list.do");
		if(service.updateStatus(ids,type)>0){
			return mv;
		}else{
			throw new ActionException("删除失败！");
		}
	}
	
	
	
	/**
	 * delete(删除功能)
	 */
	@RequestMapping(value = "/delete")
	public RedirectView delete(Long[] ids,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list.do");
		service.delete(ids);
		return mv;
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
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping(value = "/checkName")
	@ResponseBody
	public String checkName(@RequestParam("oldName") String oldName,@RequestParam("name") String name,HttpServletRequest request,HttpServletResponse response){
		if (name.equals(oldName)) {
			return "";
		} else if (service.findByName(SysPlatform.平台,name) == null) {
			return "";
		}

		return "角色名重复！";
	}
}
