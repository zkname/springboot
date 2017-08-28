package com.zkname.hd.dao;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.zkname.frame.dao.BaseDAO;
import com.zkname.hd.entity.*;

@Repository
public class SysSequenceDAO extends BaseDAO<SysSequence> {

    /**
     * 获取迭代值
     * @param seq_name
     * @return
     */
    public long nextval(String seq_name){
		String sql="select nextval('"+seq_name+"');";
        Number number = this.getJdbcTemplate().queryForObject(sql,Long.class);
        return (number != null ? number.longValue() : 0);
    }
    
    /**
     * 获取迭代值
     * @param seq_name
     * @return
     */
    public long currval(String seq_name){
		String sql="select currval('"+seq_name+"');";
        Number number = this.getJdbcTemplate().queryForObject(sql,Long.class);
        return (number != null ? number.longValue() : 0);
    }
}



