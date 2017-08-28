package com.zkname.hd.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.zkname.frame.dao.BaseDAO;
import com.zkname.hd.entity.SysUser;

@Repository
public class SysUserDAO extends BaseDAO<SysUser> {

    /**
     * 登陆
     */
    public SysUser login(String userName) {
        String sql = "select o.*  from sys_user o where o.userName=?";
        return super.findBy(sql, userName);
    }

    /**
     * getCountRegistration(判断是否可以注册)
     * (这里描述这个方法适用条件 – 可选)
     * @param userName
     * @return 
     * int
     * @exception 
     * @since  1.0.0
     */
    public int getCountRegistration(long userId, String userName) {
        StringBuffer sb = new StringBuffer("select count(*) from sys_user o where o.id!=? and o.userName=?");
        return super.queryCount(sb.toString(), userId, userName);
    }

    /**
     * 
     * findUserByUserName(查询用户是否已存在)
     * (这里描述这个方法适用条件 – 可选)
     * @param userName
     * @return 
     * SysUser
     * @exception 
     * @since  1.0.0
     */
    public SysUser findUserByUserName(String userName) {
        StringBuffer sb = new StringBuffer("select o.* from sys_user o where o.userName=? OR o.email=? ");
        return super.findBy(sb.toString(), userName, userName);

    }
  
}
