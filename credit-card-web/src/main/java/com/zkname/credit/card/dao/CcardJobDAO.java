package com.zkname.credit.card.dao;

import java.util.*;
import com.zkname.credit.card.entity.*;
import com.zkname.credit.card.session.LoginUser;
import com.zkname.core.dao.BaseDAO;
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
}



