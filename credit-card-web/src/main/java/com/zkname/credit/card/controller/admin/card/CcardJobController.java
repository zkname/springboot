package com.zkname.credit.card.controller.admin.card;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.google.common.collect.Lists;
import com.zkname.core.controller.BaseController;
import com.zkname.core.util.CompuUtils;
import com.zkname.core.util.exception.ActionException;
import com.zkname.core.util.spring.annotation.AvoidDuplicateSubmission;

@Controller
@RequestMapping(value = "/admin/card/ccardjob")
public class CcardJobController extends BaseController{
	
	// 用户业务层类
	@Autowired
	private CcardJobService service;
	
	@Autowired
	private CbankService cbankService;
	
	@Autowired
	private CcardInfoService ccardInfoService;
	
	
	@Autowired
	private CcardRangeService ccardRangeService;
	
	/**
	 * list(列表)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(PageCcardJob page,HttpServletRequest request,HttpServletResponse response) {
		page.setHttpServletRequestValue(request);
		page.setCreatorId(LoginUser.getUser().getId());
		page.setDeleStatus("1");
		page.query();
        ModelAndView mv = new ModelAndView("admin/card/ccardjob/list");
        mv.addObject("page", page);
        mv.addObject("cbanks", cbankService.getDAO().findAll(page.getCreatorId()));
        mv.addObject("ccardRanges", ccardRangeService.getDAO().findAll(page.getCreatorId()));
        mv.addObject("ccardInfos", ccardInfoService.getDAO().findAll(page.getCreatorId()));
        
		return mv;
	}
	
	/**
	 * update(修改页面)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable java.lang.Long id,HttpServletRequest request,HttpServletResponse response) {
		CcardJob entity = service.findById(id);
        ModelAndView mv = new ModelAndView("admin/card/ccardjob/update");
        mv.addObject("entity", entity);
        mv.addObject("cbank", cbankService.findById(entity.getBankId()));
        mv.addObject("ccardRange", ccardRangeService.findById(entity.getCardRangeId()));
        mv.addObject("ccardInfo", ccardInfoService.findById(entity.getCardInfoId()));
        mv.addObject("mccs", service.getDAO().findMccLimit(entity.getCardInfoId(),entity.getId()));
		return mv;
	}
	
	/**
	 * updateInput(修改提交功能)
	 */
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/updateInput", method = RequestMethod.POST)
	public RedirectView updateInput(CcardJob entity,java.lang.Long id,HttpServletRequest request,HttpServletResponse response) {
		CcardJob entityUpdate = service.findById(id);
		if(LoginUser.getUser().getId().longValue()!=entityUpdate.getCreatorId().longValue()){
			throw new ActionException("参数错误！");
		}
		//获取参数实体，操作更新实体，不拷贝字段
//		BeanUtils.copyProperties(entity,entityUpdate,new String[]{"id"});
		entityUpdate.setMoney(entity.getMoney());
		entityUpdate.setFee(entity.getFee());
		entityUpdate.setFeeValue(CompuUtils.multiply(entityUpdate.getMoney(), CompuUtils.divide(entityUpdate.getFee(), 100),2));
		entityUpdate.setStatus(entity.getStatus());
		entityUpdate.setMcc(entity.getMcc());
		entityUpdate.setUpdateTime(new Date());
		service.update(entityUpdate);
		if(StringUtils.equals(request.getParameter("calendarType"),"1")){
			RedirectView mv = new RedirectView("/admin/card/ccardcalendar/list");
			return mv;
		}
		RedirectView mv = new RedirectView("list");
		return mv;
	}
	
	/**
	 * delete(删除功能)
	 */
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public RedirectView delete(Long [] ids,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list");
		service.delete(ids);
		return mv;
	}
}
