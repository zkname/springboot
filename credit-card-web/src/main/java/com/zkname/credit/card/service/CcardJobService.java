package com.zkname.credit.card.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zkname.core.service.BaseService;
import com.zkname.core.util.CompuUtils;
import com.zkname.core.util.DateUtil;
import com.zkname.core.util.exception.ActionException;
import com.zkname.credit.card.dao.CcardJobDAO;
import com.zkname.credit.card.entity.CcardInfo;
import com.zkname.credit.card.entity.CcardJob;
import com.zkname.credit.card.entity.CcardRange;
import com.zkname.credit.card.session.LoginUser;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class CcardJobService extends BaseService<CcardJob> {
	
	@Autowired
	private CcardJobDAO dao;
	
	@Autowired
	private CcardInfoService ccardInfoService;

	@Transactional(readOnly=true)//非事务处理
	public CcardJobDAO getDAO() {
		return dao;
	}
	
    public void delete(Object [] ids){
    	for(Object id:ids){
    		CcardJob cbank=this.getDAO().findById(id);
    		if(LoginUser.getUser().getId().longValue()!=cbank.getCreatorId().longValue()){
    			throw new ActionException("参数错误！");
    		}
    		cbank.setDeleStatus("0");
    		cbank.setUpdateTime(new Date());
    		this.getDAO().update(cbank);
    	}
    }
    
    /**
     * 生成数据
     * @param cinfo
     * @param ccardRange
     */
    public void createJob(CcardInfo cinfo,CcardRange ccardRange,List<Double> moneyList){
    	String d=DateUtil.Date2Str(new Date(),"yyyy-MM");
    	d=d+"-"+String.format("%02d",cinfo.getBillDate());
    	cinfo.setJobDate(DateUtil.Str2Date(d));
    	Date e=DateUtil.addMonth(DateUtil.Str2Date(d), 1);
    	int v=DateUtil.daysBetween(cinfo.getJobDate(), e)-1;
    	List<List<Double>> list=Lists.newArrayList();
    	for(int i=0;i<v;i++){
    		if(moneyList.size()>0){
    			list.add(Lists.newArrayList(moneyList.get(0)));
    			moneyList.remove(0);
    		}else{
    			list.add(Lists.newArrayList());
    		}
    	}
		while (moneyList.size()>0) {
			int index=RandomUtils.nextInt(0,list.size());
    		list.get(index).add(moneyList.get(0));
    		moneyList.remove(0);
		}
		Collections.shuffle(list);
		for(int i=0;i<list.size();i++){
			for(int o=0;o<list.get(i).size();o++){
				CcardJob cj=new CcardJob();
				cj.setBankId(cinfo.getBankId());
				cj.setCardInfoId(cinfo.getId());
				cj.setCardRangeId(ccardRange.getId());
				cj.setCreateTime(new Date());
				cj.setCreatorId(cinfo.getCreatorId());
				cj.setDeleStatus("1");
				cj.setFee(0.6);
				cj.setJobDate(DateUtil.addDate(cinfo.getJobDate(), i+1));
				cj.setMoney(list.get(i).get(o));
				cj.setStatus(0);
				cj.setFeeValue(CompuUtils.multiply(cj.getMoney(), CompuUtils.divide(cj.getFee(), 100),2));
				this.save(cj);
			}
		}
		
		ccardInfoService.updateJobDate(cinfo.getId(),cinfo.getJobDate());
    }
    

    
    
    public static void main(String[] args) {
    	System.out.println(String.format("%02d",5));
    	
	}
}