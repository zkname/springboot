package com.zkname.frame.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.frame.dao.IBaseDAO;
import com.zkname.frame.util.DateUtil;
import com.zkname.hd.service.HdUserWalletService;

/**
 * 
 *
 * @version
 * @since Ver 1.1
 * @Date 2012-6-19
 */
@Service("BaseService")
@Transactional(rollbackFor = Exception.class)
public abstract class BaseService<T> implements IBaseService<T> {

	protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);
	
    public abstract IBaseDAO<T> getDAO();

    /**
     * 主键查询
     * @param id
     */
    @Transactional(readOnly = true)// 非事务处理
    public T findById(Object id) {
        return this.getDAO().findById(id);
    }

    /**
     * 增加修改
     */
    public void saveOrUpdate(T entity) {
        this.getDAO().saveOrUpdate(entity);
    }
    
    /**
     * 增加
     */
    public void save(T entity) {
        this.getDAO().save(entity);
    }
    /**
     * 修改
     */
    public void update(T entity) {
        this.getDAO().update(entity);
    }
    
    /**
     * 修改 固定字段
     */
    public void update(T entity,String... fields) {
        this.getDAO().update(entity,fields);
    }

    /**
     * 删除
     */
    public void delete(T entity) {
        this.getDAO().delete(entity);
    }
    
    /**
     * 主键删除
     */
    public void deleteId(java.lang.Object... id) {
        this.getDAO().deleteId(id);
    }

    public void createTable(String tab,Date d){
    	this.getDAO().createTable(tab, DateUtil.Date2Str(d,"yyyyMM"));
    }
}
