package com.zkname.credit.card.dao;

import java.util.*;
import com.zkname.credit.card.entity.*;
import com.zkname.core.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class CbankDAO extends BaseDAO<Cbank> {
	
	/**
	 * 查询用户所有添加的银行
	 * @param userId
	 * @return
	 */
	public List<Cbank> findAll(long userId){
		String sql="SELECT a.* FROM c_bank as a where (a.creatorId=? or a.creatorId=0) and a.deleStatus='1' order by a.name";
		return super.find(sql, userId);
	}
}