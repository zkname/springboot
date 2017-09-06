package com.zkname.credit.card.dao;

import org.springframework.stereotype.Repository;

import com.zkname.core.dao.BaseDAO;
import com.zkname.credit.card.entity.SysParam;


@Repository
public class SysParamDAO extends BaseDAO<SysParam> {

    
    /**
     * 
     * updateDeleStatus:(废止/恢复).
     */
	public void updateDeleStatus(long id, int status) {
		String sql = "update sys_param set deleStatus=?,updateTime=now() where id=? and deleStatus!=?";
		super.update(sql, status, id, status);
	}
    
	/**
	 * key查询
	 * 
	 * @param key
	 * @return
	 */
	public SysParam findByKey(String key) {
		String sql = "select * from sys_param where k=? ";
		return super.findBy(sql, key);
	}
}



