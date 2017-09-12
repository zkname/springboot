package com.zkname.credit.card.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zkname.core.dao.BaseDAO;
import com.zkname.credit.card.entity.CcardRange;

@Repository
public class CcardRangeDAO extends BaseDAO<CcardRange> {
	/**
	 * 查询用户所有添加的银行
	 * @param userId
	 * @return
	 */
	public List<CcardRange> findAll(long userId){
		String sql="SELECT a.* FROM c_card_range as a where (a.creatorId=? or a.creatorId=0) and a.deleStatus='1' order by a.name";
		return super.find(sql, userId);
	}
}