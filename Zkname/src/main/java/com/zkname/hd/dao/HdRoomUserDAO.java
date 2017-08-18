package com.zkname.hd.dao;

import java.util.*;
import com.zkname.hd.entity.*;
import com.zkname.frame.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class HdRoomUserDAO extends BaseDAO<HdRoomUser> {

	public int countNum(Long id) {
		String sql = "select count(1) from hd_room_user where roomId=?";
		return super.queryCount(sql, id);
	}

	public HdRoomUser findByUserId(Long userId) {
		String sql = "select o.*,u.nickname as userName,u.headimgurl from hd_room_user o left join hd_user u on o.userId=u.id where userId=?";
		return super.findBy(sql, userId);
	}

	public void deleteByRoomAndUser(Long roomId, Long userId) {
		String sql = "delete from hd_room_user where roomId=? and userId=? ";
		super.delete(sql, roomId,userId);
	}

	public List<HdRoomUser> findByRoomId(Long roomId) {
		String sql = "select * from hd_room_user where roomId=?";
		return super.find(sql, roomId);
	}

	public void deleteByRoomId(Long roomId) {
		String sql = "delete from hd_room_user where roomId=? ";
		super.delete(sql, roomId);
	}

	public HdRoomUser findOppo(Long roomId, Long userId) {
		String sql = "select o.*,u.nickname as userName,u.headimgurl from hd_room_user o left join hd_user u on o.userId=u.id where roomId=? and userId!=?";
		return super.findBy(sql, roomId,userId);
	}

	public HdRoomUser findGuestByHostId(Long hostId) {
		String sql = "select o.* from hd_room_user o left join hd_room r on o.roomId=r.id where r.userId=? and o.userId!=?";
		return super.findBy(sql, hostId, hostId);
	}

	public int updateTime(Long userId) {
		String sql = "update hd_room_user set updateTime=now() where userId=?";
		return super.update(sql, userId);
	}
	
	/**
	 * 删除房间用户
	 * @return
	 */
	public int clear(){
		String sql = "delete from hd_room_user ";
		return super.update(sql);
	}

	/**
	 * 心跳超时用户
	 * @param minutes
	 * @return
	 */
	public List<HdRoomUser> findOutOfTime(int minutes) {
		String sql ="delete from hd_room_user where date_add(updateTime, interval ? minute)<now()";
		return super.find(sql, minutes);
	}

}