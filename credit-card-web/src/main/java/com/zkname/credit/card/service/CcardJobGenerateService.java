package com.zkname.credit.card.service;

import com.zkname.credit.card.dao.*;
import com.zkname.credit.card.entity.*;
import com.zkname.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class CcardJobGenerateService extends BaseService<CcardJobGenerate> {
	
	@Autowired
	private CcardJobGenerateDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public CcardJobGenerateDAO getDAO() {
		return dao;
	}
	
    public void delete(Object [] ids){
    	for(Object id:ids){
    		this.getDAO().deleteId(id);
    	}
    }
}