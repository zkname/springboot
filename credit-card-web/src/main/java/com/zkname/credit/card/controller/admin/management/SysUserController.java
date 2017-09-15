package com.zkname.credit.card.controller.admin.management;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.zkname.credit.card.entity.SysUser;
import com.zkname.credit.card.page.PageSysUser;
import com.zkname.credit.card.service.SysUserService;
import com.zkname.credit.card.session.LoginUser;
import com.zkname.credit.card.util.EncodeUtils;
import com.zkname.credit.card.util.purview.Purview;
import com.zkname.core.controller.BaseController;
import com.zkname.core.util.exception.ActionException;

@Controller
@RequestMapping(value = "/admin/management/sysuser")
public class SysUserController extends BaseController{
	
	// 用户业务层类
	@Autowired
	private SysUserService service;
	
	@Purview(roles={Purview.管理员})
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public RedirectView login(Long id,HttpServletRequest request,HttpServletResponse response) {
		SysUser loginUser=service.findById(id);
		if(loginUser==null){
			throw new ActionException("系统错误！");
		}
		//登录
		loginUser.getLoginUser().login();
		RedirectView mv = new RedirectView("/index");
		return mv;
	}
	
	
	/**
	 * list(列表)
	 */
	@Purview(roles={Purview.管理员})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
	    PageSysUser page=(PageSysUser) request.getSession().getAttribute("SysUserController");
	    if(page==null || request.getParameterMap().containsKey("sessionRemove")){
	        page=new PageSysUser();
	        request.getSession().setAttribute("SysUserController", page);
	    }
	    page.setHttpServletRequestValue(request);
		page.query();
		
        ModelAndView mv = new ModelAndView("/admin/management/sysuser/list");
        mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * add(添加页面)
	 */
	@Purview(roles={Purview.管理员})
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("admin/management/sysuser/add");
        return mv;
	}
	
	/**
	 * addInput(添加提交功能)
	 */
	@Purview(roles={Purview.管理员})
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	public RedirectView add(SysUser entity,Long roleId,HttpServletRequest request,HttpServletResponse response) {
	    if (service.findUserByUserName(entity.getUsername()) != null) {
            throw new ActionException("用户名已存在!");
        }
	    
		entity.setCreateTime(new Date());
		entity.setPassword(EncodeUtils.md5(entity.getPassword()));
		entity.setCreatorId(1L);
		entity.setDeleStatus("1");
		entity.setUpdateTime(new Date());
		service.save(entity);
		RedirectView mv = new RedirectView("list");
		return mv;
	}
	
	
	/**
	 * update(修改页面)
	 */
	@Purview(roles={Purview.管理员})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable int id,HttpServletRequest request,HttpServletResponse response) {
		SysUser entity = service.findById(id);
        ModelAndView mv = new ModelAndView("admin/management/sysuser/update");
        mv.addObject("entity", entity);
		return mv;
	}
	
	/**
	 * updateInput(修改提交功能)
	 */
	@Purview(roles={Purview.管理员})
	@RequestMapping(value = "/updateInput", method = RequestMethod.POST)
	public RedirectView updateInput(SysUser entity,int id,Long roleId,String [] producersId,HttpServletRequest request,HttpServletResponse response) {
		SysUser entityUpdate = service.findById(id);
		//获取参数实体，操作更新实体，不拷贝字段
		if(entity.getPassword()==null || "".equals(entity.getPassword())){
			BeanUtils.copyProperties(entity,entityUpdate,new String[]{"id","password","createTime","updateTime","deleStatus","loginTime","creatorId","role"});
		}else{
			BeanUtils.copyProperties(entity,entityUpdate,new String[]{"id","createTime","updateTime","deleStatus","loginTime","creatorId","role"});
			entityUpdate.setPassword(EncodeUtils.md5(entity.getPassword()));
		}
		entityUpdate.setUpdateTime(new Date());
		service.update(entityUpdate);
		RedirectView mv = new RedirectView("list");
		return mv;
	}
	
	/**
	 * delete(废止功能)
	 */
	@Purview(roles={Purview.管理员})
	@RequestMapping(value = "/delete")
	public RedirectView delete(Long [] ids,String type,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list");
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
	public String checkLoginName(@RequestParam("oldLoginName") String oldLoginName,@RequestParam("username") String username,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		
		if (username.equals(oldLoginName)) {
			return null;
		} else if (service.findUserByUserName(username) != null) {
			return "用户名已存在";
		}
		return null;
	}
    
    
}
