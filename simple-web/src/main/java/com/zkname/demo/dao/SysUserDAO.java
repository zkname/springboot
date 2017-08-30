package com.zkname.demo.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.zkname.frame.dao.BaseDAO;
import com.zkname.demo.entity.SysUser;

@Repository
public class SysUserDAO extends BaseDAO<SysUser> {

    /**
     * 登陆
     */
    public SysUser login(String userName) {
        String sql = "select o.*  from sys_user o where o.username=?";
        return super.findBy(sql, userName);
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
  
}
