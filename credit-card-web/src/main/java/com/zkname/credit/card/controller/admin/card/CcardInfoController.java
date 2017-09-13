package com.zkname.credit.card.controller.admin.card;

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
import com.zkname.core.util.exception.ActionException;
import com.zkname.core.util.spring.annotation.AvoidDuplicateSubmission;
import com.zkname.credit.card.entity.CcardInfo;
import com.zkname.credit.card.page.PageCcardInfo;
import com.zkname.credit.card.service.CbankService;
import com.zkname.credit.card.service.CcardInfoService;
import com.zkname.credit.card.service.CcardRangeService;
import com.zkname.credit.card.session.LoginUser;

@Controller
@RequestMapping(value = "/admin/card/ccardinfo")
public class CcardInfoController extends BaseController{
	
	// 用户业务层类
	@Autowired
	private CcardInfoService service;
	
	@Autowired
	private CbankService cbankService;
	
	@Autowired
	private CcardRangeService ccardRangeService;
	
	/**
	 * list(列表)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(PageCcardInfo page,HttpServletRequest request,HttpServletResponse response) {
		page.setHttpServletRequestValue(request);
		page.setCreatorId(LoginUser.getUser().getId());
		page.setDeleStatus("1");
		page.query();
        ModelAndView mv = new ModelAndView("admin/card/ccardinfo/list");
        mv.addObject("page", page);
        mv.addObject("cbanks", cbankService.getDAO().findAll(page.getCreatorId()));
        mv.addObject("ccardRanges", ccardRangeService.getDAO().findAll(page.getCreatorId()));
		return mv;
	}
	
	/**
	 * add(添加页面)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
		
        ModelAndView mv = new ModelAndView("admin/card/ccardinfo/add");
        mv.addObject("cbanks", cbankService.getDAO().findAll(LoginUser.getUser().getId()));
        mv.addObject("ccardRanges", ccardRangeService.getDAO().findAll(LoginUser.getUser().getId()));
		return mv;
	}
	
	/**
	 * addInput(添加提交功能)
	 */
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	public RedirectView add(CcardInfo entity,HttpServletRequest request,HttpServletResponse response) {
		entity.setCreateTime(new Date());
		entity.setCreatorId(LoginUser.getUser().getId());
		entity.setDeleStatus("1");
		entity.setUpdateTime(new Date());
		service.save(entity);
		RedirectView mv = new RedirectView("list");
		return mv;
	}
	
	
	/**
	 * update(修改页面)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable java.lang.Long id,HttpServletRequest request,HttpServletResponse response) {
		CcardInfo entity = service.findById(id);
        ModelAndView mv = new ModelAndView("admin/card/ccardinfo/update");
        mv.addObject("cbanks", cbankService.getDAO().findAll(LoginUser.getUser().getId()));
        mv.addObject("ccardRanges", ccardRangeService.getDAO().findAll(LoginUser.getUser().getId()));
        mv.addObject("entity", entity);
		return mv;
	}
	
	/**
	 * updateInput(修改提交功能)
	 */
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/updateInput", method = RequestMethod.POST)
	public RedirectView updateInput(CcardInfo entity,java.lang.Long id,HttpServletRequest request,HttpServletResponse response) {
		CcardInfo entityUpdate = service.findById(id);
		if(LoginUser.getUser().getId().longValue()!=entityUpdate.getCreatorId().longValue()){
			throw new ActionException("参数错误！");
		}
		entity.setJobDate(entityUpdate.getJobDate());
		Long cardRangeId=entity.getCardRangeId();
		//获取参数实体，操作更新实体，不拷贝字段
		BeanUtils.copyProperties(entity,entityUpdate,new String[]{"id","createTime","updateTime","deleStatus","creatorId","jobDate"});
		entityUpdate.setNextUp(entity.getNextUp());
		service.update(cardRangeId,entityUpdate);
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
