package com.zkname.hd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.hd.dao.SysUserPurviewDAO;
import com.zkname.frame.dao.IBaseDAO;
import com.zkname.hd.entity.SysUserPurview;
import com.zkname.frame.service.BaseService;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysUserPurviewService extends BaseService<SysUserPurview> {
	
	@Autowired
	private SysUserPurviewDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public IBaseDAO<SysUserPurview> getDAO() {
		return dao;
	}
	
    /**
     * 用户id查询
     * @param userId
     * @return
     */
	@Transactional(readOnly=true)//非事务处理
    public SysUserPurview getSysUserPurview(long userId){
	    return this.dao.getSysUserPurview(userId);
	}
	
}