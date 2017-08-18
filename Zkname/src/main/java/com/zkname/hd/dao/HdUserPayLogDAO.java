package com.zkname.hd.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.zkname.frame.dao.ShardingBaseDAO;
import com.zkname.hd.entity.HdUserPayLog;

@Repository
public class HdUserPayLogDAO extends ShardingBaseDAO<HdUserPayLog> {
	
    /**
     * 查询订单
     * @param payCode
     * @param payType
     * @return
     */
    public HdUserPayLog findByPayCode(String payCode,int payType,Date d){
        String sql = "select * from hd_user_pay_log where payCode=? and payType=? and createTime=?";
        return super.findBy(sql,payCode,payType,d);
    }
    
    /**
     * 查询订单
     * @param payCode
     * @return
     */
    public HdUserPayLog findByPayCode(String payCode,Date d){
        String sql = "select * from hd_user_pay_log where payCode=? and createTime=?";
        return super.findBy(sql,payCode,d);
    }
    
    /**
     * 付款成功更新
     * @param entity
     */
    public int updatePaySuccess(HdUserPayLog entity){
        String sql = "update hd_user_pay_log set userRechargeLogId=?,payStatus=?,payTime=?,payId=?,payReturnValue=? where id=? and createTime=?";
        return super.update(sql, entity.getUserRechargeLogId(),entity.getPayStatus(),entity.getPayTime(),entity.getPayId(),entity.getPayReturnValue(),entity.getId(),entity.getCreateTime());
    }
    
    /**
     * 更新状态
     * @param entity
     * @return
     */
    public int updatePayStatus(HdUserPayLog entity){
        String sql = "update hd_user_pay_log set userRechargeLogId=?,payStatus=?,payTime=?,payId=?,payReturnValue=? where id=? and createTime=?";
        return super.update(sql, entity.getUserRechargeLogId(),entity.getPayStatus(),entity.getPayTime(),entity.getPayId(),entity.getPayReturnValue(),entity.getId(),entity.getCreateTime());
    }
    
    /**
     * 更新PayId
     * @param entity
     */
    public int updatePayId(HdUserPayLog entity){
        String sql = "update hd_user_pay_log set payId=? where id=? and createTime=?";
        return super.update(sql, entity.getPayId(),entity.getId(),entity.getCreateTime());
    }
}