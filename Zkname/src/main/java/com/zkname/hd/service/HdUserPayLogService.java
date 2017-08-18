package com.zkname.hd.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zkname.frame.service.BaseService;
import com.zkname.frame.util.CompuUtils;
import com.zkname.hd.dao.HdUserPayLogDAO;
import com.zkname.hd.entity.HdShop;
import com.zkname.hd.entity.HdShopWallet;
import com.zkname.hd.entity.HdShopWalletLog;
import com.zkname.hd.entity.HdUserPayLog;
import com.zkname.hd.entity.HdUserRechargeLog;
import com.zkname.hd.entity.SysParam;

@Service
@Transactional(value="transactionManager1",rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class HdUserPayLogService extends BaseService<HdUserPayLog> {
	
	@Autowired
	private HdUserPayLogDAO dao;
	
	@Autowired
	private HdShopService hdShopService;
	
	@Autowired
	private HdUserWalletService hdUserWalletService;
	
	@Autowired
	private HdUserRechargeLogService hdUserRechargeLogService;
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private HdShopWalletService hdShopWalletService;
	
	@Autowired
	private HdShopWalletLogService hdShopWalletLogService;

	@Transactional(readOnly=true)//非事务处理
	public HdUserPayLogDAO getDAO() {
		return dao;
	}
	
    public void delete(Object [] ids){
    	for(Object id:ids){
    		this.getDAO().deleteId(id);
    	}
    }
    
    public void update(HdUserPayLog hdUserPayLog){
    	this.getDAO().update(hdUserPayLog);
    }
    
    /**
     * 支付成功
     * @param vmPayLog
     */
    @Transactional(value="chainedTransactionManager",rollbackFor = Exception.class) 
    public void updatePaySuccess(HdUserPayLog hdUserPayLog){
    	//记录充值记录
		HdUserRechargeLog hdUserRechargeLog=new HdUserRechargeLog();
		hdUserRechargeLog.setCreateTime(hdUserPayLog.getCreateTime());
		hdUserRechargeLog.setCreatorId(0L);
		hdUserRechargeLog.setDeleStatus("1");
		hdUserRechargeLog.setMoney(hdUserPayLog.getTotalMoney().intValue());
		hdUserRechargeLog.setShopId(hdUserPayLog.getShopId());
		hdUserRechargeLog.setUpdateTime(new Date());
		hdUserRechargeLog.setUserId(hdUserPayLog.getUserId());
    	//写入回扣
    	int goldCoin=rebate(hdUserPayLog,hdUserRechargeLog,hdUserPayLog.getTotalMoney());
    	//增加金币
    	hdUserWalletService.addGoldCoin(hdUserPayLog,goldCoin);
    	//记录充值记录写入
		hdUserRechargeLogService.save(hdUserRechargeLog);
		hdUserPayLog.setUserRechargeLogId(hdUserRechargeLog.getId());
    	//更新支付订单数据
    	//减少货量,更新支付订单数据
    	if(this.getDAO().updatePaySuccess(hdUserPayLog)<1){
    		throw new RuntimeException();
    	}
    }
    
    /**
     * 回扣给企业
     * @param shopId
     * @param totalMoney
     * @return 充值后的金币量goldCoin
     */
    private int rebate(HdUserPayLog hdUserPayLog,HdUserRechargeLog hdUserRechargeLog,long totalMoney){
    	long shopId=hdUserPayLog.getShopId();
    	HdShop hdShop=hdShopService.findById(shopId);
    	//充值比例
    	int rechargePercentage=hdShop.getRechargePercentage();
    	hdUserRechargeLog.setRechargePercentage(rechargePercentage);
    	//分成比例
    	int returnPercentage=hdShop.getReturnPercentage();
    	int moren = sysParamService.findByKey(SysParam.人民币兑换金币默认值).getIntV();
    	int fencheng=(int) CompuUtils.multiply(CompuUtils.divide(returnPercentage,100,2), totalMoney);
    	//分成
    	add(hdUserPayLog,HdShopWallet.返利,fencheng,hdShop);
    	return (int) CompuUtils.multiply(CompuUtils.divide(totalMoney,moren,2), rechargePercentage);
    }
    
	/**
	 * 分成增加
	 * @param type
	 * @param fencheng
	 * @param hdShop
	 */
    private void add(HdUserPayLog hdUserPayLog,int type,int fencheng,HdShop hdShop){
		HdShopWallet hdShopWallet=hdShopWalletService.findById(hdShop.getId());
		if(hdShopWallet==null){
			hdShopWallet=new HdShopWallet();
			hdShopWallet.setShopId(hdShop.getId());
			hdShopWallet.setCreateTime(new Date());
			hdShopWallet.setHistoryMoney(0L);
			hdShopWallet.setMoney(0L);
			hdShopWalletService.save(hdShopWallet);
		}
		HdShopWalletLog hdShopWalletLog=new HdShopWalletLog();
		hdShopWalletLog.setAddMoney((long)fencheng);
		hdShopWalletLog.setCreateTime(hdUserPayLog.getCreateTime());
		hdShopWalletLog.setOriginalMoney(hdShopWallet.getMoney());
		hdShopWalletLog.setShopId(hdShop.getId());
		hdShopWalletLog.setType(type);
		hdShopWalletLogService.save(hdShopWalletLog);
		hdShopWalletService.getDAO().addMoney(hdShop.getId(),fencheng);
	}
    
    /**
     * 只更新支付状态
     * @param vmPayLog
     */
    public int updatePayStatus(HdUserPayLog vmPayLog){
    	return this.getDAO().updatePayStatus(vmPayLog);
    }
    
    /**
     * 只更新订单id
     * @param vmPayLog
     * @return
     */
    public int updatePayId(HdUserPayLog vmPayLog){
    	return this.getDAO().updatePayId(vmPayLog);
    }
}