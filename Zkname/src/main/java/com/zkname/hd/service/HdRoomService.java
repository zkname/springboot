package com.zkname.hd.service;

import com.zkname.hd.dao.*;
import com.zkname.hd.entity.*;
import com.zkname.hd.util.CodeUtil;
import com.zkname.hd.util.memcache.IMemSyn;
import com.zkname.hd.util.memcache.MemcacheSynchronize;
import com.zkname.hd.vo.UserStatusVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zkname.frame.service.BaseService;
import com.zkname.frame.util.memcache.XMemcacheKey;
import com.zkname.frame.util.memcache.XMemcachedClientImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class) // 注解实现事务，所有异常都回滚；
public class HdRoomService extends BaseService<HdRoom> {

	@Autowired
	private HdRoomDAO dao;

	@Autowired
	private HdRoomUserService hdRoomUserService;

	@Resource(name = "baseXMemcachedClient")
	private XMemcachedClientImp baseXMemcachedClient;

	@Transactional(readOnly = true) // 非事务处理
	public HdRoomDAO getDAO() {
		return dao;
	}

	public void delete(Long[] ids) {
		for (Long id : ids) {
			this.getDAO().updateStatus(id, 0);
		}
	}

	public void recovery(Long[] ids) {
		for (Long id : ids) {
			this.getDAO().updateStatus(id, 1);
		}
	}

	/**
	 * 创建更新房间
	 * 
	 * @param room
	 */
	public String saveUpdate(HdRoom room, Long userId) {
		saveOrUpdate(room);
		HdRoomUser roomUser = hdRoomUserService.getDAO().findByUserId(userId);
		if (roomUser == null) {
			// 房间用户
			roomUser = new HdRoomUser();
			roomUser.setRoomId(room.getId());
			roomUser.setUserId(room.getUserId());
			roomUser.setRoleId(0L);
			roomUser.setStatus(HdRoomUser.STATUS_活跃);
			roomUser.setCreateTime(new Date());
			hdRoomUserService.save(roomUser);
		} else if (room.getId().longValue() != roomUser.getRoomId().longValue()) {
			return CodeUtil.getJson(CodeUtil.已在其他房间);
		}
		return null;
	}

	/**
	 * 进入房间
	 * 
	 * @param userId
	 * @param room
	 * @return
	 */
	public String enterRoom(Long userId, HdRoom room) {
		// 判断房间状态
		if (room.getStatus().intValue() != HdRoom.STATUS_开放) {
			return CodeUtil.getJson(CodeUtil.房间未开放);
		}
		HdRoomUser roomUser = hdRoomUserService.getDAO().findByUserId(userId);
		// 判断是否已进入房间
		if (roomUser != null) {
			if (roomUser.getRoomId().longValue() == room.getId().longValue()) {
				return null;
			}
			return CodeUtil.getJson(CodeUtil.已在其他房间);
		}
		// 判断房间人数
		int num = hdRoomUserService.getDAO().countNum(room.getId());
		if (num >= room.getMaxNum().intValue()) {
			return CodeUtil.getJson(CodeUtil.房间已满);
		}
		// 房间用户
		roomUser = new HdRoomUser();
		roomUser.setRoomId(room.getId());
		roomUser.setUserId(userId);
		roomUser.setRoleId(0L);
		roomUser.setStatus(HdRoomUser.STATUS_活跃);
		roomUser.setCreateTime(new Date());
		hdRoomUserService.save(roomUser);
		// 房间状态
		getDAO().updateRoomStatus(room.getId(), HdRoom.STATUS_关闭);
		return null;
	}

	/**
	 * 退出房间
	 * 
	 * @param room
	 * @param userId
	 */
	public void exit(HdRoom room, Long userId) {
		hdRoomUserService.getDAO().deleteByRoomAndUser(room.getId(), userId);
		getDAO().updateRoomStatus(room.getId(), HdRoom.STATUS_开放);
		// 更新房主缓存
		HdRoomUser hostUser = hdRoomUserService.getDAO().findOppo(room.getId(), userId);
		if (hostUser == null) {
			return;
		}
		UserStatusVo hostStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + hostUser.getUserId()));
		if (hostStatus != null) {
			IMemSyn syn1 = () -> {
				hostStatus.setRoomChangedTime(System.currentTimeMillis());
				hostStatus.setGameKey(null);
				hostStatus.setLastGameKey(null);
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + hostUser.getUserId()), 172800, hostStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + hostUser.getUserId()), MemcacheSynchronize.EXP_方法同步超时秒, syn1);
		}
	}

	/**
	 * 解散房间
	 * 
	 * @param room
	 * @param userId
	 */
	public void dismiss(HdRoom room, Long userId) {
		// 玩家
		HdRoomUser guestUser = hdRoomUserService.getDAO().findGuestByHostId(userId);
		// 清空房间
		hdRoomUserService.getDAO().deleteByRoomId(room.getId());
		// 更新房间状态
		getDAO().updateRoomStatus(room.getId(), HdRoom.STATUS_关闭);
		// 更新玩家缓存
		if (guestUser == null) {
			return;
		}
		UserStatusVo guestStatus = baseXMemcachedClient.get(XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestUser.getUserId()));
		if (guestStatus != null) {
			IMemSyn syn1 = () -> {
				guestStatus.setRoomChangedTime(System.currentTimeMillis());
				guestStatus.setGameKey(null);
				guestStatus.setLastGameKey(null);
				baseXMemcachedClient.set(XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestUser.getUserId()), 172800, guestStatus);
				return true;
			};
			MemcacheSynchronize.memcacheSyn(baseXMemcachedClient, XMemcacheKey.getKey(XMemcacheKey.用户状态 + guestUser.getUserId()), MemcacheSynchronize.EXP_方法同步超时秒, syn1);
		}
	}

	/**
	 * 关闭所有房间
	 * 
	 * @return
	 */
	public int clear() {
		return this.getDAO().clear();
	}
}