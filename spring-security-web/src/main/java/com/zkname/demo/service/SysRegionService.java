package com.zkname.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.core.service.BaseService;
import com.zkname.demo.dao.SysRegionDAO;
import com.zkname.demo.entity.SysRegion;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysRegionService extends BaseService<SysRegion> {
	
	@Autowired
	private SysRegionDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public SysRegionDAO getDAO() {
		return dao;
	}
	
    public void delete(java.lang.Integer [] ids){
    	for(java.lang.Integer id:ids){
    		this.getDAO().deleteId(id);
    	}
    }
    
}
