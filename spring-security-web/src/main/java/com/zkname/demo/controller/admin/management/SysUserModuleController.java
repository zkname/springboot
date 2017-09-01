package com.zkname.demo.controller.admin.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.zkname.core.controller.BaseController;
import com.zkname.demo.entity.SysUserModule;
import com.zkname.demo.service.SysPlatformService;
import com.zkname.demo.service.SysUserModuleService;
import com.zkname.core.util.ParamType;

@Controller
@RequestMapping(value = "/admin/management/sysusermodule")
public class SysUserModuleController extends BaseController{
	
	// 用户业务层类
	@Autowired
	private SysUserModuleService service;
	
	@Autowired
	private SysPlatformService isysSysPlatformService;
	
	/**
	 * list(列表)
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
		int platformId=ParamType.getint(request.getParameter("platformId"));
		if(platformId==0)platformId=1;
        ModelAndView mv = new ModelAndView("admin/management/sysusermodule/list");
        mv.addObject("menuVo", service.getPurviewAndMenu(platformId));
        mv.addObject("sysPlatformList", isysSysPlatformService.findAll());
        mv.addObject("platformId", platformId);
		return mv;
	}
	
	/**
	 * add(添加页面)
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
		
        ModelAndView mv = new ModelAndView("admin/management/sysusermodule/add");
		return mv;
	}
	
	/**
	 * addInput(添加提交功能)
	 */
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	public RedirectView add(SysUserModule entity,HttpServletRequest request,HttpServletResponse response) {
		service.saveOrUpdate(entity);
		RedirectView mv = new RedirectView("list.do");
		return mv;
	}
	
	
	/**
	 * update(修改页面)
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable int id,HttpServletRequest request,HttpServletResponse response) {
		SysUserModule entity = service.findById(id);
        ModelAndView mv = new ModelAndView("admin/management/sysusermodule/update");
        mv.addObject("entity", entity);
		return mv;
	}
	
	/**
	 * updateInput(修改提交功能)
	 */
	@RequestMapping(value = "/updateInput", method = RequestMethod.POST)
	public RedirectView updateInput(SysUserModule entity,int id,HttpServletRequest request,HttpServletResponse response) {
		SysUserModule entityUpdate = service.findById(id);
		//获取参数实体，操作更新实体，不拷贝字段
		BeanUtils.copyProperties(entity,entityUpdate,new String[]{"id"});
		service.saveOrUpdate(entityUpdate);
		RedirectView mv = new RedirectView("list.do");
		return mv;
	}
	
	
	/**
	 * delete(删除功能)
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RedirectView delete(Long [] ids,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list.do");
		service.deleteId(ids);
		return mv;
	}
}
