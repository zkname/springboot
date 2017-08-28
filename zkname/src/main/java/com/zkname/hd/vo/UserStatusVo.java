package com.zkname.hd.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class UserStatusVo implements Serializable {

	private static final long serialVersionUID = 1359910180228066904L;

	public static final int 需要更新 = 1;
	public static final int 不需要更新 = 0;

	@Getter
	@Setter
	private Long roomChangedTime;
	@Getter
	@Setter
	private String lastGameKey;
	@Getter
	@Setter
	private String gameKey;
	@Getter
	@Setter
	private Long gameChangedTime;
	@Getter
	@Setter
	private Long moneyChangedTime;
	
}
