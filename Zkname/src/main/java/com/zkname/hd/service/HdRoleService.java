package com.zkname.hd.service;

import com.zkname.hd.dao.*;
import com.zkname.hd.entity.*;
import com.zkname.frame.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class HdRoleService extends BaseService<HdRole> {
	
	@Autowired
	private HdRoleDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public HdRoleDAO getDAO() {
		return dao;
	}
	
    public void delete(Long [] ids){
    	for(Long id:ids){
    		this.getDAO().updateStatus(id,0);
    	}
    }
    
    public void recovery(Long [] ids){
    	for(Long id:ids){
    		this.getDAO().updateStatus(id,1);
    	}
    }
}