package com.zkname.hd.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;


public class UserInfoVo implements Serializable {

	private static final long serialVersionUID = 3961661279747638116L;
	
	@Getter
	@Setter
	private String openId;
	@Getter
	@Setter
	private String nickName;
	@Getter
	@Setter
	private int gender;
	@Getter
	@Setter
	private String language;
	@Getter
	@Setter
	private String city;
	@Getter
	@Setter
	private String province;
	@Getter
	@Setter
	private String country;
	@Getter
	@Setter
	private String avatarUrl;
	@Getter
	@Setter
	private String unionId;


	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		.append("openid",getOpenId())
		.append("nickname",getNickName())
		.append("gender",getGender())
		.append("language",getLanguage())
		.append("city",getCity())
		.append("province",getProvince())
		.append("country",getCountry())
		.append("avatarUrl",getAvatarUrl())
		.append("unionid",getUnionId())
		.toString();
	}
}
