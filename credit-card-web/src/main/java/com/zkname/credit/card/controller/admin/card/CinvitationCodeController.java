package com.zkname.credit.card.controller.admin.card;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.zkname.credit.card.page.*;
import com.zkname.credit.card.entity.*;
import com.zkname.credit.card.service.*;
import com.zkname.credit.card.session.LoginUser;
import com.zkname.core.controller.BaseController;
import com.zkname.core.util.exception.ActionException;
import com.zkname.core.util.spring.annotation.AvoidDuplicateSubmission;

@Controller
@RequestMapping(value = "/admin/card/cinvitationcode")
public class CinvitationCodeController extends BaseController{
	
	// 用户业务层类
	@Autowired
	private CinvitationCodeService service;
	
	/**
	 * list(列表)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(PageCinvitationCode page,HttpServletRequest request,HttpServletResponse response) {
		if(LoginUser.getUser().getId()!=1L){
			throw new ActionException("系统错误！");
		}
		page.setHttpServletRequestValue(request);
		page.query();
        ModelAndView mv = new ModelAndView("admin/card/cinvitationcode/list");
        mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * delete(删除功能)
	 */
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public RedirectView delete(Long [] ids,HttpServletRequest request,HttpServletResponse response) {
		if(LoginUser.getUser().getId()!=1L){
			throw new ActionException("系统错误！");
		}
		RedirectView mv = new RedirectView("list");
		service.delete(ids);
		return mv;
	}
}
