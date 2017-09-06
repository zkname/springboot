package com.zkname.credit.card.service;

import com.zkname.credit.card.dao.*;
import com.zkname.credit.card.entity.*;
import com.zkname.credit.card.session.LoginUser;
import com.zkname.core.service.BaseService;
import com.zkname.core.util.exception.ActionException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class CcardRangeService extends BaseService<CcardRange> {
	
	@Autowired
	private CcardRangeDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public CcardRangeDAO getDAO() {
		return dao;
	}
	
    public void delete(Object [] ids){
    	for(Object id:ids){
    		CcardRange cbank=this.getDAO().findById(id);
    		if(LoginUser.getUser().getId().longValue()!=cbank.getCreatorId().longValue()){
    			throw new ActionException("参数错误！");
    		}
    		cbank.setDeleStatus("0");
    		cbank.setUpdateTime(new Date());
    		this.getDAO().update(cbank);
    	}
    }
}