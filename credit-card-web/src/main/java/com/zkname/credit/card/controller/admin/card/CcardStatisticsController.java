package com.zkname.credit.card.controller.admin.card;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zkname.core.controller.BaseController;
import com.zkname.core.util.DateUtil;
import com.zkname.core.util.spring.annotation.AvoidDuplicateSubmission;
import com.zkname.credit.card.page.PageCcardStatistics;
import com.zkname.credit.card.service.CbankService;
import com.zkname.credit.card.service.CcardInfoService;
import com.zkname.credit.card.service.CcardRangeService;
import com.zkname.credit.card.session.LoginUser;

@Controller
@RequestMapping(value = "/admin/card/ccardstatistics")
public class CcardStatisticsController extends BaseController{
	
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
	public ModelAndView list(PageCcardStatistics page,HttpServletRequest request,HttpServletResponse response) {
		page.setHttpServletRequestValue(request);
		page.setCreatorId(LoginUser.getUser().getId());
		page.setDeleStatus("1");
		if(StringUtils.isBlank(page.getDate()) || page.getDate().length()!=7){
			page.setDate(DateUtil.Date2Str(new Date(),"yyyy-MM"));
		}
		page.setPageNo(-1);
		page.setPageSize(-1);
		page.query();
        ModelAndView mv = new ModelAndView("admin/card/ccardstatistics/list");
        mv.addObject("page", page);
		return mv;
	}
}
