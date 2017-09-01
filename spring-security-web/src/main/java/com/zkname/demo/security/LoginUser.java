package com.zkname.demo.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆session对象
 */
public class LoginUser extends User implements IUser{
	
	
	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 4405044834730876477L;

	//主键
	@Getter
	@Setter
	private Long id;
	//email地址
	@Getter
	@Setter
	private String email;
	//登陆时间
	@Getter
	@Setter
	private Date loginTime;
	//密码
	@Getter
	@Setter
	private String pass;
	//真实姓名
	@Getter
	@Setter
	private String realName;
	//用户角色编码
	@Getter
	@Setter
	private String roleCode;
	//所属平台
	@Getter
	@Setter
	private List<Integer> platformIds;

	public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	@Override
	public Set<String> getPurview() {
		Set<String> set=Sets.newHashSet();
		Collection<GrantedAuthority> cs=super.getAuthorities();
		for(GrantedAuthority ga:cs){
			set.add(ga.getAuthority());
		}
		return set;
	}
}

