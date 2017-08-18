package com.zkname.hd.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.frame.service.BaseService;
import com.zkname.hd.dao.HdShopWalletDAO;
import com.zkname.hd.entity.HdShop;
import com.zkname.hd.entity.HdShopWallet;
import com.zkname.hd.entity.HdShopWalletLog;
import com.zkname.hd.entity.HdUserPayLog;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class HdShopWalletService extends BaseService<HdShopWallet> {
	
	@Autowired
	private HdShopWalletDAO dao;
	
	@Autowired
	private HdShopWalletLogService hdShopWalletLogService;

	@Transactional(readOnly=true)//非事务处理
	public HdShopWalletDAO getDAO() {
		return dao;
	}
}