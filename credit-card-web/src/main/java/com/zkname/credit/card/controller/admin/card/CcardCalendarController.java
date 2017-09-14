package com.zkname.credit.card.controller.admin.card;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.zkname.core.controller.BaseController;
import com.zkname.core.util.CompuUtils;
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
		List<Map<String,Object>> list=service.getDAO().findUserJob(LoginUser.getUser().getId(),DateUtil.Str2Date(start),DateUtil.Str2Date(end));
		Map<String,Map<String,Object>> map=Maps.newLinkedHashMap();
		for(Map<String,Object> obj:list){
			int status=(Integer) obj.get("status");
			String d=(String) obj.get("start");
			double money=(Double)obj.get("money");
			double fee=(Double)obj.get("fee");
			obj.remove("money");
			obj.remove("fee");
			Map<String,Object> map2=null;
			if(!map.containsKey(d)){
				map2=Maps.newLinkedHashMap();
				map2.put("start", d);
				map2.put("status", 2);
				map2.put("money", 0D);
				map2.put("nomoney", 0D);
				map2.put("fee", 0D);
				map.put(d, map2);
			}else{
				map2=map.get(d);
			}
			if(status==1){
				map2.put("money", CompuUtils.add(money,(Double) map2.get("money"),2));
				map2.put("fee", CompuUtils.add(fee,(Double) map2.get("fee"),2));
			}else{
				map2.put("nomoney", CompuUtils.add(money,(Double) map2.get("nomoney"),2));
			}
		}
		list.addAll(0,map.values());
		return JsonUtil.toJSONString(list);
	}
	
}
