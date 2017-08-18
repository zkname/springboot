package com.zkname.hd.service;

import com.zkname.hd.dao.*;
import com.zkname.hd.entity.*;
import com.zkname.frame.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class HdRoomUserService extends BaseService<HdRoomUser> {
	
	@Autowired
	private HdRoomUserDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public HdRoomUserDAO getDAO() {
		return dao;
	}

	/**
	 * 删除房间用户
	 * @return
	 */
	public int clear(){
		return this.getDAO().clear();
	}

	public void updateTime(Long userId) {
		this.getDAO().updateTime(userId);
	}

}