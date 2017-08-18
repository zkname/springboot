package com.zkname.hd.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.frame.service.BaseService;
import com.zkname.frame.util.exception.DaoException;
import com.zkname.hd.dao.HdUserDAO;
import com.zkname.hd.entity.HdUser;
import com.zkname.hd.entity.HdUserWallet;
import com.zkname.hd.entity.HdUserWalletLog;

@Service
@Transactional(rollbackFor = Exception.class) // 注解实现事务，所有异常都回滚；
public class HdUserService extends BaseService<HdUser> {

	@Autowired
	private HdUserDAO dao;

	@Autowired
	private HdUserWalletService hdUserWalletService;

	@Autowired
	private HdUserWalletLogService hdUserWalletLogService;

	@Transactional(readOnly = true) // 非事务处理
	public HdUserDAO getDAO() {
		return dao;
	}

	public void delete(Long[] ids) {
		for (Long id : ids) {
			this.getDAO().updateStatus(id, 0);
		}
	}

	public void recovery(Long[] ids) {
		for (Long id : ids) {
			this.getDAO().updateStatus(id, 1);
		}
	}
}