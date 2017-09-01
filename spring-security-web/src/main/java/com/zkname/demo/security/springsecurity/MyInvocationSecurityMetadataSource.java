package com.zkname.demo.security.springsecurity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zkname.demo.service.SysUserModuleService;
import com.zkname.demo.vo.MenuVo;

/**
 * 
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * 
 */
@Component
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private AntPathMatcher urlMatcher = new AntPathMatcher();
	//平台权限
	private Map<String, Collection<ConfigAttribute>> resourceMap = null;
	//平台菜单
	private MenuVo purviewMenuTemplate;
	//权限更新时间
	private static long timeout=0;
	
	@Autowired
	private SysUserModuleService iadminSysUserModuleService;
	
	public MyInvocationSecurityMetadataSource() {
		
	}
	
	/**
	 * loadResourceDefine(加载权限)
	 * (这里描述这个方法适用条件 – 可选) 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void loadResourceDefine() {
		//获取本平台所以权限
//		SysUserModuleService iadminSysUserModuleService=SpringContextHolder.getBean(SysUserModuleService.class);
		Map<String, Collection<String>> map=iadminSysUserModuleService.getPurviewTemplate(1);
		resourceMap = transform(map);
		purviewMenuTemplate=iadminSysUserModuleService.getMenuTemplate(1);
	}
	
	private Map<String, Collection<ConfigAttribute>> transform(Map<String, Collection<String>> map){
		
		Map<String, Collection<ConfigAttribute>> mapConfigAttribute=Maps.newHashMap();

	    for(Map.Entry<String, Collection<String>> entry : map.entrySet())   
	    {   
	    	List<ConfigAttribute> list=Lists.newArrayList();
	    	for(String value1:entry.getValue()){
	    		list.add(new SecurityConfig(value1));
	    	}
	    	mapConfigAttribute.put(entry.getKey(), list);
	    }
	    return mapConfigAttribute;
	}
	

	// According to a URL, Find out permission configuration of this URL.
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// guess object is a URL.
		this.getPurviewMenuTemplate();
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if(resURL==null)continue;
			if (urlMatcher.match(resURL, url)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}
	
	public MenuVo getPurviewMenuTemplate() {
		long i=System.currentTimeMillis();
		if(i-timeout>180000){
			synchronized (MyInvocationSecurityMetadataSource.class) {
				//权限缓存3分钟
				if(i-timeout>180000){
					loadResourceDefine();
					timeout=i;
				}
			}
		}
		return purviewMenuTemplate;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return Lists.newArrayList();
	}
}