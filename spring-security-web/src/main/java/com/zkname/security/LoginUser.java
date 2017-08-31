package com.yuewuxian.frame.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.google.common.collect.Sets;

/**
 * 登陆session对象
 * 
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-1-10
 */
public class LoginUser extends User implements IUser{
	
	
	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 4405044834730876477L;

	//主键
	private Long id;
	//email地址
	private String email;
	//登陆时间
	private Date loginTime;
	//密码
	private String pass;
	//真实姓名
	private String realName;
	//用户角色编码
	private String roleCode;
	//所属平台
	private List<Integer> platformIds;

	public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUserName() {
		return super.getUsername();
	}

	public String getPassWord() {
		return null;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

    public List<Integer> getPlatformIds() {
        return platformIds;
    }

    public void setPlatformIds(List<Integer> platformIds) {
        this.platformIds = platformIds;
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

