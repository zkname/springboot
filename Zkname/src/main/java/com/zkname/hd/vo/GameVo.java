package com.zkname.hd.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GameVo implements Serializable {
	
	private static final long serialVersionUID = 1359910180228066904L;
	
	public static final int 对局中 = 1;
	public static final int 已结束 = 2;
	
	@Getter
	@Setter
	private String key;
	
	@Getter
	@Setter
	private Long creatorId;
	
	@Getter
	@Setter
	private Long betNum;
	
	@Getter
	@Setter
	private Long winnerId;
	
	@Getter
	@Setter
	private Integer status;
	
}
