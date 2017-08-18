package com.zkname.hd.entity;

import com.zkname.hd.entity.base.*;

public class HdRoom extends BaseHdRoom{
	
	private static final long serialVersionUID = -3451888062016242057L;
	public static final Integer TYPE_一对一 = 1;
	
	public static final Integer STATUS_关闭 = 0;
	public static final Integer STATUS_开放 = 1;
	
	public HdRoom(){
	}
	
	public HdRoom(
		java.lang.Long id
	){
		
		super(
				id
			);
		
	}
}



