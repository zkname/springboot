package com.zkname.credit.card.controller.admin.card;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkname.core.controller.BaseController;
import com.zkname.core.util.DateUtil;
import com.zkname.core.util.jackson.JsonUtil;
import com.zkname.credit.card.service.CcardJobService;
import com.zkname.credit.card.session.LoginUser;

@Controller
@RequestMapping(value = "/admin/card/ccardcalendar")
public class CcardCalendarController extends BaseController{
	
	@Autowired
	private CcardJobService service;
	
	/**
	 * list(日历)
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("admin/card/ccardcalendar/list");
		return mv;
	}
	
	
	
	
	/**
	 * list(日历)
	 */
	@ResponseBody
	@RequestMapping(value = "/data", produces = "application/text; charset=utf-8" ,method = RequestMethod.GET)
	public String data(String start,String end,HttpServletRequest request,HttpServletResponse response) {
		return JsonUtil.toJSONString(service.getDAO().findUserJob(LoginUser.getUser().getId(),DateUtil.Str2Date(start),DateUtil.Str2Date(end)));
	}
	
}
