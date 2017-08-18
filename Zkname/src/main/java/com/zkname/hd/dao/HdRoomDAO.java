package com.zkname.hd.dao;

import java.util.*;
import com.zkname.hd.entity.*;
import com.zkname.frame.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class HdRoomDAO extends BaseDAO<HdRoom> {

	public void updateStatus(Long id, int status) {
		String sql = "update hd_room o set o.deleStatus=?,o.updateTime=now() where o.id=? and o.deleStatus!=?";
		super.update(sql, status, id, status);
	}

	public HdRoom findByUserId(Long userId) {
		String sql="select * from hd_room where userId=?";
		return super.findBy(sql, userId);
	}

	public int updateRoomStatus(Long id, Integer status) {
		String sql = "update hd_room o set o.status=?,o.updateTime=now() where o.id=?";
		return super.update(sql, status, id);
	}
	
	
	/**
	 * 关闭所有房间
	 * @return
	 */
	public int clear(){
		String sql = "update hd_room o set o.status=?";
		return super.update(sql, 0);
	}
}