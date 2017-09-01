package com.zkname.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.demo.dao.SysPlatformDAO;
import com.zkname.demo.entity.SysPlatform;
import com.zkname.core.dao.IBaseDAO;
import com.zkname.core.service.BaseService;

@Service
@Transactional(rollbackFor = Exception.class)
// 注解实现事务，所有异常都回滚；
public class SysPlatformService extends BaseService<SysPlatform> {

    @Autowired
    private SysPlatformDAO dao;

    @Transactional(readOnly=true)//非事务处理
    public IBaseDAO<SysPlatform> getDAO() {
        return dao;
    }

    /**
    * 查询所有
    */
    @Transactional(readOnly=true)//非事务处理
    public List<SysPlatform> findAll(){
        return this.dao.findAll();
    }
}