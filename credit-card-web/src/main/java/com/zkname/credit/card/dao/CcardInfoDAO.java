package com.zkname.credit.card.dao;

import java.util.*;
import com.zkname.credit.card.entity.*;
import com.zkname.core.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class CcardInfoDAO extends BaseDAO<CcardInfo> {
	
	/**
	 * 查询未生成任务的信用卡
	 * @param billDate
	 * @param d
	 * @return
	 */
    public List<CcardInfo> findBillDate(int billDate,Date d) {
        String sql = "SELECT * FROM c_card_info where deleStatus='1' and  billDate=? and jobDate!=?";
        return super.find(sql, billDate,d);
    }
    
    
    /**
     * 更新任务任务生成时间
     * @param cinfoId
     * @param jobDate
     */
    public int updateJobDate(long cinfoId,Date jobDate){
    	String sql = "update c_card_info set jobDate=? where id=?";
    	return super.update(sql, jobDate,cinfoId);
    }
    
    
	/**
	 * 查询用户所有信用卡
	 * @param userId
	 * @return
	 */
	public List<CcardInfo> findAll(long userId){
		String sql="SELECT a.* FROM c_card_info as a where a.creatorId=? and a.deleStatus='1' order by a.id";
		return super.find(sql, userId);
	}
}