package com.zkname.demo.controller.admin.system;

import java.util.Date;

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
import com.zkname.demo.entity.SysParam;
import com.zkname.demo.page.PageSysParam;
import com.zkname.demo.security.springsecurity.SpringSecurityUtils;
import com.zkname.demo.service.SysParamService;

@Controller
@RequestMapping(value = "/admin/system/sysparam")
public class SysParamController extends BaseController{
	
	// 用户业务层类
	@Autowired
	private SysParamService service;
	
	/**
	 * list(列表)
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(PageSysParam page,HttpServletRequest request,HttpServletResponse response) {
		page.setHttpServletRequestValue(request);
		page.query();
        ModelAndView mv = new ModelAndView("admin/system/sysparam/list");
        mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * add(添加页面)
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
		
        ModelAndView mv = new ModelAndView("admin/system/sysparam/add");
		return mv;
	}
	
	/**
	 * addInput(添加提交功能)
	 */
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	public RedirectView add(SysParam entity,HttpServletRequest request,HttpServletResponse response) {
		entity.setType(0);
		entity.setCreateTime(new Date());
		entity.setCreatorId(SpringSecurityUtils.getLoginUser().getId().longValue());
		entity.setDeleStatus("1");
		service.save(entity);
		RedirectView mv = new RedirectView("list.do");
		return mv;
	}
	
	
	/**
	 * update(修改页面)
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable java.lang.Long id,HttpServletRequest request,HttpServletResponse response) {
		SysParam entity = service.findById(id);
		service.findByKey(entity.getK());
        ModelAndView mv = new ModelAndView("admin/system/sysparam/update");
        mv.addObject("entity", entity);
		return mv;
	}
	
	/**
	 * updateInput(修改提交功能)
	 */
	@RequestMapping(value = "/updateInput", method = RequestMethod.POST)
	public RedirectView updateInput(SysParam entity,java.lang.Long id,HttpServletRequest request,HttpServletResponse response) {
		SysParam entityUpdate = service.findById(id);
		//获取参数实体，操作更新实体，不拷贝字段
		BeanUtils.copyProperties(entity,entityUpdate,new String[]{"id","type","createTime","deleStatus"});
		entityUpdate.setUpdateTime(new Date());
		entityUpdate.setCreatorId(SpringSecurityUtils.getLoginUser().getId().longValue());
		service.update(entityUpdate);
//		service.clearfindByKey(entityUpdate.getK());
		RedirectView mv = new RedirectView("list.do");
		return mv;
	}
	
	/**
	 * delete(删除功能)
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public RedirectView delete(java.lang.Long [] ids,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list.do");
		service.delete(ids);
		return mv;
	}
	
	/**
	 * reuse(恢复功能)
	 */
	@RequestMapping(value = "/recovery", method = RequestMethod.GET)
	public RedirectView recovery(java.lang.Long [] ids,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list.do");
		service.recovery(ids);
		return mv;
	}
}
