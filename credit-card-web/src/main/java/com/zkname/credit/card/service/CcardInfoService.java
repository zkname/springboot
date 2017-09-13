package com.zkname.credit.card.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.core.service.BaseService;
import com.zkname.core.util.exception.ActionException;
import com.zkname.credit.card.dao.CcardInfoDAO;
import com.zkname.credit.card.entity.CcardInfo;
import com.zkname.credit.card.entity.CcardJobGenerate;
import com.zkname.credit.card.session.LoginUser;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class CcardInfoService extends BaseService<CcardInfo> {
	
	@Autowired
	private CcardInfoDAO dao;
	
	@Autowired
	private CcardJobService ccardJobService;
	
	@Autowired
	private CcardJobGenerateService ccardJobGenerateService;

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
    
    /**
     * 
     * @param cardRangeId
     * @param entity
     */
    public void update(Long cardRangeId,CcardInfo entity){
    	if(entity.getJobDate()!=null && entity.getCardRangeId().longValue()!=cardRangeId.longValue()){
    		entity.setJobDate(null);
    		
        	CcardJobGenerate ccardJobGenerate=new CcardJobGenerate();
        	ccardJobGenerate.setBankId(entity.getBankId());
        	ccardJobGenerate.setCardInfoId(entity.getId());
        	ccardJobGenerate.setCardRangeId(entity.getCardRangeId());
        	ccardJobGenerate.setCreateTime(new Date());
        	ccardJobGenerateService.save(ccardJobGenerate);
    		
//    		ccardJobService.clear(entity.getId(),cardRangeId);
    	}
    	this.getDAO().update(entity);
    }
    
    
    
    /**
     * 
     * @param cardRangeId
     * @param entity
     */
    public void save(CcardInfo entity){
    	this.getDAO().save(entity);
    	CcardJobGenerate ccardJobGenerate=new CcardJobGenerate();
    	ccardJobGenerate.setBankId(entity.getBankId());
    	ccardJobGenerate.setCardInfoId(entity.getId());
    	ccardJobGenerate.setCardRangeId(entity.getCardRangeId());
    	ccardJobGenerate.setCreateTime(new Date());
    	ccardJobGenerateService.save(ccardJobGenerate);
    }
    
    /**
     * 生成
     * @param cinfo
     */
    public void updateGenerate(CcardInfo cinfo){
    	if(this.getDAO().updateJobDate(cinfo.getId(),cinfo.getJobDate())>0){
        	CcardJobGenerate ccardJobGenerate=new CcardJobGenerate();
        	ccardJobGenerate.setBankId(cinfo.getBankId());
        	ccardJobGenerate.setCardInfoId(cinfo.getId());
        	ccardJobGenerate.setCardRangeId(cinfo.getCardRangeId());
        	ccardJobGenerate.setCreateTime(new Date());
        	ccardJobGenerateService.save(ccardJobGenerate);
    	}
    }
}