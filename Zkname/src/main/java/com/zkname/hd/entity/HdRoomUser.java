package com.zkname.hd.entity;

import com.zkname.hd.entity.base.*;

import lombok.Getter;
import lombok.Setter;

public class HdRoomUser extends BaseHdRoomUser{
	
	private static final long serialVersionUID = -3451888062016242057L;

	public static final Integer STATUS_活跃 = 1;
	public static final Integer STATUS_非活跃 = 0;
	
	/** 用户名 */
	@Getter
	@Setter
	private java.lang.String userName;
	/** 房间名 */
	@Getter
	@Setter
	private java.lang.String roomName;
	/** 头像url */
	@Getter
	@Setter
	private java.lang.String headimgurl;
	
	public HdRoomUser(){
	}
	
	public HdRoomUser(
		java.lang.Long id
	){
		
		super(
				id
			);
		
	}
}



