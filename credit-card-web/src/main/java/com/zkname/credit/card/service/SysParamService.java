package com.zkname.credit.card.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.core.service.BaseService;
import com.zkname.credit.card.dao.SysParamDAO;
import com.zkname.credit.card.entity.SysParam;
import com.zkname.credit.card.util.task.MinuteJobTask;

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
	
	
	public void update(SysParam sy){
		this.getDAO().update(sy);
		//更新内存
		MinuteJobTask.UPDATE(sy);
	}
}
