package com.zkname.credit.card.dao;

import java.util.*;
import com.zkname.credit.card.entity.*;
import com.zkname.credit.card.session.LoginUser;
import com.zkname.core.dao.BaseDAO;
import com.zkname.core.util.DateUtil;

import org.springframework.stereotype.Repository;

@Repository
public class CcardJobDAO extends BaseDAO<CcardJob> {
	/**
	 * 返回任务列表
	 * @param userId
	 * @param b
	 * @param e
	 * @return
	 */
	public List<Map<String,Object>> findUserJob(long userId,Date b,Date e){
		String sql="SELECT a.id,CONCAT(c.name,'-',b.name,'[',a.money,'元]',',手续[',a.feeValue,'元]') as title,date_format(a.jobDate,'%Y-%m-%d') as start,a.status as status,a.money as money,a.feeValue as fee FROM c_card_job as a ,c_card_info as b,c_bank as c where a.creatorId=? and a.jobDate>=? and a.jobDate<? and c.id=a.bankId and b.id=a.cardInfoId order by a.jobDate asc,a.status desc";
		return super.getJdbcTemplate().queryForList(sql, userId,b,e);
	}
	
	
	public List<String> findMccLimit(long cardInfoId,long id){
		String sql="SELECT a.mcc FROM c_card_job as a where a.id!=? and a.status='1' and a.cardInfoId=? and a.mcc is not null order by a.jobDate asc limit 20";
		return super.getJdbcTemplate().queryForList(sql, String.class,id,cardInfoId);
	}
	
    /**
     * 更换规则清理数据
     * @param cardInfoId
     * @param cardRangeId
     */
    public int clear(long cardInfoId,long cardRangeId){
    	String sql = "DELETE FROM c_card_job WHERE cardInfoId=? AND cardRangeId=? AND status=0 AND jobDate>=?";
    	return super.update(sql, cardInfoId,cardRangeId,DateUtil.getNowDate());
    }
}



