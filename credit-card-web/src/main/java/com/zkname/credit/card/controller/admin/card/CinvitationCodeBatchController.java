package com.zkname.credit.card.controller.admin.card;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.net.URLCodec;
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
import com.zkname.credit.card.util.EncodeUtils;
import com.zkname.credit.card.util.purview.Purview;
import com.csvreader.CsvWriter;
import com.google.common.base.Charsets;
import com.zkname.core.controller.BaseController;
import com.zkname.core.util.exception.ActionException;
import com.zkname.core.util.spring.annotation.AvoidDuplicateSubmission;

@Controller
@RequestMapping(value = "/admin/card/cinvitationcodebatch")
public class CinvitationCodeBatchController extends BaseController{
	
	// 用户业务层类
	@Autowired
	private CinvitationCodeBatchService service;
	
	@Autowired
	private CinvitationCodeService cinvitationCodeService;
	
	/**
	 * list(列表)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@Purview(roles={Purview.管理员})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(PageCinvitationCodeBatch page,HttpServletRequest request,HttpServletResponse response) {
		page.setHttpServletRequestValue(request);
		page.query();
        ModelAndView mv = new ModelAndView("admin/card/cinvitationcodebatch/list");
        mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * add(添加页面)
	 */
	@Purview(roles={Purview.管理员})
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("admin/card/cinvitationcodebatch/add");
		return mv;
	}
	
	/**
	 * addInput(添加提交功能)
	 */
	@Purview(roles={Purview.管理员})
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	public RedirectView add(CinvitationCodeBatch entity,HttpServletRequest request,HttpServletResponse response) {
		entity.setCreateTime(new Date());
		entity.setCreatorId(LoginUser.getUser().getId());
		entity.setDeleStatus("1");
		entity.setIsGenerate("0");
		entity.setUpdateLock("0");
		service.save(entity);
		RedirectView mv = new RedirectView("list");
		return mv;
	}
	
	/**
	 * delete(删除功能)
	 */
	@Purview(roles={Purview.管理员})
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public RedirectView delete(Long [] ids,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list");
		service.delete(ids);
		return mv;
	}
	
	@Purview(roles={Purview.管理员})
	@RequestMapping(value = "/listDow", method = RequestMethod.GET)
	public void listDow(Long id,HttpServletRequest request, HttpServletResponse response) {
		CinvitationCodeBatch cinvitationCodeBatch=service.findById(id);
		try(OutputStream ouputStream = response.getOutputStream();){
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download;charset=UTF-8");
			// 文件名
			String fileName = cinvitationCodeBatch.getId()+".csv";
			response.setHeader("Content-Disposition", "attachment;filename=\"" + new URLCodec("UTF-8").encode(fileName) + "\"");
			
			ouputStream.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
			CsvWriter wr = new CsvWriter(ouputStream, ',', Charsets.UTF_8);
			String[] strs = { "邀请码"};
			wr.writeRecord(strs);
			List<CinvitationCode> list=cinvitationCodeService.getDAO().findBatchId(cinvitationCodeBatch.getId());
			for (CinvitationCode bo : list) {
				wr.writeRecord(new String[]{bo.getInvitationCode()});
			}
			wr.flush();
			wr.close();
		} catch (ActionException e1) {
			throw e1;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
