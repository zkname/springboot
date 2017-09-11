package com.zkname.credit.card.dao;

import org.springframework.stereotype.Repository;

import com.zkname.core.dao.BaseDAO;
import com.zkname.credit.card.entity.SysUser;

@Repository
public class SysUserDAO extends BaseDAO<SysUser> {

    /**
     * 登陆
     */
    public SysUser login(String userName) {
        String sql = "select o.*  from sys_user o where (o.username=? or o.email=?) and o.deleStatus='1'";
        return super.findBy(sql, userName,userName );
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
        StringBuffer sb = new StringBuffer("select o.* from sys_user o where o.username=?");
        return super.findBy(sb.toString(), userName);

    }

    /**
     * 
     * findUserByEmail(查询用户是否已存在)
     * (这里描述这个方法适用条件 – 可选)
     * @param userName
     * @return 
     * SysUser
     * @exception 
     * @since  1.0.0
     */
    public SysUser findUserByEmail(String email) {
        StringBuffer sb = new StringBuffer("select o.* from sys_user o where o.email=?");
        return super.findBy(sb.toString(), email);

    }
 }
