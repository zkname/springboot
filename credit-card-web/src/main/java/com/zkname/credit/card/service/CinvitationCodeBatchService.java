package com.zkname.credit.card.service;

import com.zkname.credit.card.dao.*;
import com.zkname.credit.card.entity.*;
import com.zkname.credit.card.session.LoginUser;
import com.google.common.collect.Lists;
import com.zkname.core.service.BaseService;
import com.zkname.core.util.exception.ActionException;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class CinvitationCodeBatchService extends BaseService<CinvitationCodeBatch> {
	
	@Autowired
	private CinvitationCodeBatchDAO dao;
	
	@Autowired
	private CinvitationCodeService cinvitationCodeService;
	
	@Transactional(readOnly=true)//非事务处理
	public CinvitationCodeBatchDAO getDAO() {
		return dao;
	}
	
    public void delete(Object [] ids){
    	for(Object id:ids){
    		CinvitationCodeBatch cbank=this.getDAO().findById(id);
    		if(LoginUser.getUser().getId().longValue()!=cbank.getCreatorId().longValue()){
    			throw new ActionException("参数错误！");
    		}
    		cbank.setDeleStatus("0");
    		cbank.setUpdateTime(new Date());
    		this.getDAO().update(cbank);
    	}
    }
    
    public void createCode(CinvitationCodeBatch ccb, Set<String> set){
    	ccb.setIsGenerate("1");
    	this.update(ccb, "isGenerate");
    	
    	for(String code:set){
    		CinvitationCode cc=new CinvitationCode();
    		cc.setCreateTime(new Date());
    		cc.setCreatorId(1L);
    		cc.setDeleStatus("1");
    		cc.setInvitationCode(code);
    		cc.setInvitationCodeBatch(ccb.getId());
    		cc.setUserId(0L);
    		cinvitationCodeService.save(cc);
    	}
    }
}