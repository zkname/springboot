package com.zkname.hd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.hd.dao.*;
import com.zkname.hd.entity.*;
import com.zkname.frame.service.BaseService;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysSequenceService extends BaseService<SysSequence> {
	
	@Autowired
	private SysSequenceDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public SysSequenceDAO getDAO() {
		return dao;
	}
	
    public void delete(java.lang.String [] ids){
    	for(java.lang.String id:ids){
    		this.getDAO().deleteId(id);
    	}
    }
    
    /**
     * 获取迭代值
     * @param seq_name
     * @return
     */
    public long nextval(String seq_name){
    	return this.getDAO().nextval(seq_name);
    }
}
