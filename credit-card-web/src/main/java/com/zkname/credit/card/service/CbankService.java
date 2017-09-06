package com.zkname.credit.card.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.core.service.BaseService;
import com.zkname.core.util.exception.ActionException;
import com.zkname.credit.card.dao.CbankDAO;
import com.zkname.credit.card.entity.Cbank;
import com.zkname.credit.card.session.LoginUser;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class CbankService extends BaseService<Cbank> {
	
	@Autowired
	private CbankDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public CbankDAO getDAO() {
		return dao;
	}
	
    public void delete(Object [] ids){
    	for(Object id:ids){
    		Cbank cbank=this.getDAO().findById(id);
    		if(LoginUser.getUser().getId().longValue()!=cbank.getCreatorId().longValue()){
    			throw new ActionException("参数错误！");
    		}
    		cbank.setDeleStatus("0");
    		cbank.setUpdateTime(new Date());
    		this.getDAO().update(cbank);
    	}
    }
}