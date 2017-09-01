package com.zkname.demo.security.springsecurity;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.zkname.demo.security.LoginUser;
import com.zkname.demo.service.SysUserService;
import com.zkname.demo.vo.SysUserVo;


@Component
public class MyUserDetailService implements UserDetailsService {
	
	@Autowired
	private SysUserService sysUserService;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		SysUserVo sysUserVo= sysUserService.login(username ,1l);
		if (sysUserVo!=null ) {
			if(!sysUserVo.getDeleStatus().equals("1")){
				throw new UsernameNotFoundException("该用户已被锁定，请联系管理员！");
			}
			ArrayList<String> grantedAuthoritys = sysUserVo.getGrantedAuthoritys();
			ArrayList<SimpleGrantedAuthority> auths = Lists.newArrayList();
			if(grantedAuthoritys!=null){
				for(String sga:grantedAuthoritys){
					auths.add(new SimpleGrantedAuthority(sga));
				}
			}
			LoginUser user = new LoginUser(sysUserVo.getUserName(), sysUserVo.getPassword(), true, true, true, true, auths);
			user.setPass(sysUserVo.getPassword());
			user.setId(sysUserVo.getId());
			user.setEmail(sysUserVo.getEmail());
			user.setLoginTime(sysUserVo.getLoginTime());
			user.setRealName(sysUserVo.getRealName());
			user.setRoleCode(sysUserVo.getRoleCode());
			return user;
			
		}
		throw new UsernameNotFoundException("该用户不存在！");
	}

}