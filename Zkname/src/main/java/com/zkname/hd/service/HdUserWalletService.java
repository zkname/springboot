package com.zkname.hd.service;

import com.zkname.hd.controller.ApiController;
import com.zkname.hd.dao.*;
import com.zkname.hd.entity.*;
import com.zkname.hd.util.CodeUtil;
import com.zkname.frame.service.BaseService;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
@Transactional(rollbackFor = Exception.class) // 注解实现事务，所有异常都回滚；
public class HdUserWalletService extends BaseService<HdUserWallet> {

	@Autowired
	private HdUserWalletDAO dao;

	@Autowired
	private HdUserWalletLogService hdUserWalletLogService;

	@Autowired
	private HdRoomUserService hdRoomUserService;

	@Autowired
	private SysParamService sysParamService;

	@Transactional(readOnly = true) // 非事务处理
	public HdUserWalletDAO getDAO() {
		return dao;
	}

	/**
	 * 金豆交易
	 * 
	 * @param userWallet
	 * @param toUserWallet
	 * @param beanNum
	 * @param roomId
	 * @return
	 */
	@Transactional(value="chainedTransactionManager",rollbackFor = Exception.class) 
	public String trade(Long userId, Long toUserId, Long beanNum, int game) {
		// 判断是否是同一个人
		if (userId.longValue() == toUserId.longValue()) {
			return CodeUtil.getJson(CodeUtil.不能跟自己交易);
		}
		// 判断金豆数量
		HdUserWallet userWallet = dao.findByUserId(userId);
		if (userWallet == null || userWallet.getGoldBean().longValue() < beanNum.longValue()) {
			return CodeUtil.getJson(CodeUtil.金豆不足);
		}
		// 房主
		HdRoomUser roomUser = hdRoomUserService.getDAO().findByUserId(userId);
		if (roomUser == null) {
			return CodeUtil.getJson(CodeUtil.错误);
		}
		// 玩家
		HdRoomUser toRoomUser = hdRoomUserService.getDAO().findByUserId(toUserId);
		if (toRoomUser == null) {
			return CodeUtil.getJson(CodeUtil.玩家退出);
		}
		// 是否同一房间
		if (roomUser.getRoomId() != toRoomUser.getRoomId()) {
			return CodeUtil.getJson(CodeUtil.不在同一房间);
		}
		Date date = new Date();
		// 金豆增加用户
		HdUserWallet toUserWallet = dao.findByUserId(toUserId);
		if (toUserWallet == null) {
			toUserWallet = new HdUserWallet();
			toUserWallet.setUserId(toUserId);
			toUserWallet.setGoldCoin(0L);
			toUserWallet.setGoldBean(0L);
			toUserWallet.setCreateTime(date);
			save(toUserWallet);
		}
		// 金豆交易
		if (dao.updateGoldBean(userId, -beanNum, date) > 0 && dao.updateGoldBean(toUserId, beanNum, date) > 0) {
			// 金豆日志
			HdUserWalletLog userWalletLog = new HdUserWalletLog();
			userWalletLog.setUserId(userId);
			userWalletLog.setRoomId(roomUser.getRoomId());
			userWalletLog.setBeanType(game == 0 ? HdUserWalletLog.BEAN_TYPE_金豆交易减 : HdUserWalletLog.BEAN_TYPE_游戏输金豆);
			userWalletLog.setHistoryGoldBean(userWallet.getGoldBean());
			userWalletLog.setAddGoldBean(-beanNum);
			userWalletLog.setFromUser(userId);
			userWalletLog.setToUser(toUserId);
			userWalletLog.setCreateTime(date);
			hdUserWalletLogService.save(userWalletLog);
			HdUserWalletLog toUserWalletLog = new HdUserWalletLog();
			toUserWalletLog.setUserId(toUserId);
			toUserWalletLog.setRoomId(toRoomUser.getRoomId());
			toUserWalletLog.setBeanType(game == 0 ? HdUserWalletLog.BEAN_TYPE_金豆交易加 : HdUserWalletLog.BEAN_TYPE_游戏赢金豆);
			toUserWalletLog.setHistoryGoldBean(userWallet.getGoldBean());
			toUserWalletLog.setAddGoldBean(beanNum);
			toUserWalletLog.setFromUser(userId);
			toUserWalletLog.setToUser(toUserId);
			toUserWalletLog.setCreateTime(date);
			hdUserWalletLogService.save(toUserWalletLog);
			return null;
		}
		return CodeUtil.getJson(CodeUtil.失败);
	}

	/**
	 * 增加金币
	 * 
	 * @param hdUserPayLog
	 *            订单号
	 * @param goldCoin
	 *            金币
	 */
	public void addGoldCoin(HdUserPayLog hdUserPayLog, int goldCoin) {
		// 金币增加用户
		HdUserWallet userWallet = dao.findByUserId(hdUserPayLog.getUserId());
		if (userWallet == null) {
			userWallet = new HdUserWallet();
			userWallet.setUserId(hdUserPayLog.getUserId());
			userWallet.setGoldCoin(0L);
			userWallet.setGoldBean(0L);
			userWallet.setCreateTime(new Date());
			save(userWallet);
		}
		// 金豆交易
		dao.updateGoldCoin(hdUserPayLog.getUserId(), goldCoin, new Date());
		// 金币日志
		HdUserWalletLog userWalletLog = new HdUserWalletLog();
		userWalletLog.setUserId(hdUserPayLog.getUserId());
		userWalletLog.setRoomId(0L);
		userWalletLog.setCoinType(HdUserWalletLog.COIN_TYPE_金币充值);
		userWalletLog.setBeanType(0);
		userWalletLog.setHistoryGoldBean(userWallet.getGoldBean());
		userWalletLog.setAddGoldBean(0L);
		userWalletLog.setHistoryGoldCoin(userWallet.getGoldCoin());
		userWalletLog.setAddGoldCoin((long) goldCoin);
		userWalletLog.setFromUser(hdUserPayLog.getUserId());
		userWalletLog.setToUser(hdUserPayLog.getUserId());
		userWalletLog.setCreateTime(new Date());
		hdUserWalletLogService.save(userWalletLog);
	}

	/**
	 * 金币兑换金豆
	 * 
	 * @param hdUser
	 * @param goldCoin
	 */
	public String goldCoinToGoldBean(HdUser hdUser, HdUserWallet hdUserWallet, int goldCoin) {
		int goldBean = goldCoin * sysParamService.findByKey(SysParam.金币兑换金豆比例).getIntV();
		if (dao.updateGoldCoinToGoldBean(hdUser.getId(), goldCoin, goldBean) < 1) {
			return CodeUtil.getJson(CodeUtil.可兑换金币不足);
		}
		// 金币日志
		HdUserWalletLog userWalletLog = new HdUserWalletLog();
		userWalletLog.setUserId(hdUser.getId());
		userWalletLog.setRoomId(0L);
		userWalletLog.setCoinType(HdUserWalletLog.COIN_TYPE_兑换金豆);
		userWalletLog.setBeanType(HdUserWalletLog.BEAN_TYPE_兑换金豆);
		userWalletLog.setHistoryGoldBean(hdUserWallet.getGoldBean());
		userWalletLog.setAddGoldBean((long) goldBean);
		userWalletLog.setHistoryGoldCoin(hdUserWallet.getGoldCoin());
		userWalletLog.setAddGoldCoin((long) -goldCoin);
		userWalletLog.setFromUser(hdUser.getId());
		userWalletLog.setToUser(hdUser.getId());
		userWalletLog.setCreateTime(new Date());
		hdUserWalletLogService.save(userWalletLog);
		return null;
	}

	/**
	 * 清空金豆
	 * 
	 * @return
	 */
	public int clearGoldBean() {
		return this.getDAO().clearGoldBean();
	}
}