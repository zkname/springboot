package com.zkname.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.demo.dao.SysParamDAO;
import com.zkname.demo.entity.SysParam;
import com.zkname.core.service.BaseService;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysParamService extends BaseService<SysParam> {
	
	@Autowired
	private SysParamDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public SysParamDAO getDAO() {
		return dao;
	}
	
	/**
	 * 废止
	 * 
	 * @param ids
	 */
	public void delete(java.lang.Long[] ids) {
		for (java.lang.Long id : ids) {
			this.getDAO().updateDeleStatus(id, 0);
		}
	}

	/**
	 * 恢复
	 * 
	 * @param ids
	 */
	public void recovery(Long[] ids) {
		for (java.lang.Long id : ids) {
			this.getDAO().updateDeleStatus(id, 1);
		}
	}
    
	@Transactional(readOnly=true)//非事务处理
	public SysParam findByKey(String key){
		return this.getDAO().findByKey(key);
	}
}
