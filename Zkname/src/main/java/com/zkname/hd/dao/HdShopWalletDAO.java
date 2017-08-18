package com.zkname.hd.dao;

import org.springframework.stereotype.Repository;
import com.zkname.frame.dao.BaseDAO;
import com.zkname.hd.entity.HdShopWallet;

@Repository
public class HdShopWalletDAO extends BaseDAO<HdShopWallet> {
	/**
	 * 增加钱包里的钱
	 * @param shopId
	 * @param fencheng
	 * @return
	 */
	public int addMoney(long shopId,int fencheng){
		String sql="update hd_shop_wallet set historyMoney=historyMoney+?,money=money+?,updateTime=NOW() where shopId=?";
		return this.update(sql, fencheng, fencheng, shopId);
	}
}