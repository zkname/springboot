package com.zkname.hd.service;

import com.zkname.hd.dao.*;
import com.zkname.hd.entity.*;
import com.zkname.frame.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value="transactionManager1",rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class HdUserRechargeLogService extends BaseService<HdUserRechargeLog> {
	
	@Autowired
	private HdUserRechargeLogDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public HdUserRechargeLogDAO getDAO() {
		return dao;
	}
	
}