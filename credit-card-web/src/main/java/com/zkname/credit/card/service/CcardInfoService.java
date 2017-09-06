package com.zkname.credit.card.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.core.service.BaseService;
import com.zkname.core.util.exception.ActionException;
import com.zkname.credit.card.dao.CcardInfoDAO;
import com.zkname.credit.card.entity.CcardInfo;
import com.zkname.credit.card.session.LoginUser;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class CcardInfoService extends BaseService<CcardInfo> {
	
	@Autowired
	private CcardInfoDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public CcardInfoDAO getDAO() {
		return dao;
	}
	
    public void delete(Object [] ids){
    	for(Object id:ids){
    		CcardInfo cbank=this.getDAO().findById(id);
    		if(LoginUser.getUser().getId().longValue()!=cbank.getCreatorId().longValue()){
    			throw new ActionException("参数错误！");
    		}
    		cbank.setDeleStatus("0");
    		cbank.setUpdateTime(new Date());
    		this.getDAO().update(cbank);
    	}
    }
    
    /**
     * 更新任务任务生成时间
     * @param cinfoId
     * @param jobDate
     */
    public void updateJobDate(long cinfoId,Date jobDate){
    	this.getDAO().updateJobDate(cinfoId,jobDate);
    }
}