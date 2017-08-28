package com.zkname.frame.util.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware
{

	private static ApplicationContext applicationContext;

	public SpringContextHolder()
	{
	}

	public void setApplicationContext(ApplicationContext applicationContext)
	{
		SpringContextHolder.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext()
	{
		checkApplicationContext();
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T  getBean(String name)
	{
		checkApplicationContext();
		return (T) SpringContextHolder.applicationContext.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> clazz)
	{
		checkApplicationContext();
		return (T) SpringContextHolder.applicationContext.getBean(clazz);
	}

	private static void checkApplicationContext()
	{
		if (SpringContextHolder.applicationContext == null)
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		else
			return;
	}
}