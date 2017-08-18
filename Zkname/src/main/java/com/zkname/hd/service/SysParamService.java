package com.zkname.hd.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.frame.service.BaseService;
import com.zkname.frame.util.memcache.XMemcachedClientImp;
import com.zkname.hd.dao.SysParamDAO;
import com.zkname.hd.entity.SysParam;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysParamService extends BaseService<SysParam> {
	
	@Autowired
	private SysParamDAO dao;
	
	@Resource(name = "baseXMemcachedClient")
	private XMemcachedClientImp baseXMemcachedClient;

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
		SysParam qp = baseXMemcachedClient.get("SysParamService.findByKey"+key);
		if(qp==null){
			qp = this.getDAO().findByKey(key);
			if(qp==null){
				return null;
			}
			baseXMemcachedClient.set("SysParamService.findByKey"+key, 600, qp);
		}
		return qp;
	}
	
	@Transactional(readOnly = true) // 非事务处理
	public void clearfindByKey(String key){
		baseXMemcachedClient.delete("SysParamService.findByKey"+key);
	}
}
