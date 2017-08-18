package com.zkname.hd.dao;

import java.util.*;
import com.zkname.hd.entity.*;
import com.zkname.frame.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class HdUserWalletDAO extends BaseDAO<HdUserWallet> {

	public HdUserWallet findByUserId(Long userId) {
		String sql = "select * from hd_user_wallet where userId=?";
		return super.findBy(sql, userId);
	}

	public int updateGoldBean(Long userId, long addBean, Date date) {
		String sql = "update hd_user_wallet set goldBean=goldBean+?,updateTime=? where userId=? ";
		if (addBean < 0) {
			sql += " and goldBean>=? ";
			return super.update(sql, addBean, date, userId, Math.abs(addBean));
		} else {
			return super.update(sql, addBean, date, userId);
		}
	}
	
	/**
	 * 金币充值
	 * @param userId
	 * @param goldCoin
	 * @param date
	 * @return
	 */
	public int updateGoldCoin(Long userId, long goldCoin,Date date){
		String sql = "update hd_user_wallet set goldCoin=goldCoin+?,updateTime=? where userId=?";
		return super.update(sql, goldCoin,date,userId);
	}

	
	/**
	 * 兑换金豆
	 * @param userId
	 * @param reduceGoldCoin
	 * @param addBean
	 * @param date
	 */
	public int updateGoldCoinToGoldBean(Long userId, long reduceGoldCoin,long addBean) {
		String sql = "update hd_user_wallet set goldCoin=goldCoin+?,goldBean=goldBean+?,updateTime=NOW() where userId=? and goldCoin>=?";
		return super.update(sql,-reduceGoldCoin,addBean,userId,reduceGoldCoin);
	}
	
	
	/**
	 * 清空金豆
	 * @return
	 */
	public int clearGoldBean(){
		String sql = "update hd_user_wallet set goldBean=0";
		return super.update(sql);
	}
}