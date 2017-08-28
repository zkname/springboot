package com.zkname.frame.util.spring.filter;

import javax.servlet.Filter;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.OrderUtils;

import com.google.common.collect.Lists;

public class ServletContextInitializerBeansExtend extends ServletContextInitializerBeansModify {

	public ServletContextInitializerBeansExtend(ListableBeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	protected void addServletContextInitializerBean(Class<?> type, String beanName,
			ServletContextInitializer initializer, ListableBeanFactory beanFactory, Object source) {
		Integer order = OrderUtils.getOrder(source.getClass());
		if (order == null && source instanceof Ordered) {
			order = ((Ordered) source).getOrder();
		}
		if("securityFilterChainRegistration".equals(beanName)){
			DelegatingFilterProxyRegistrationBean filterRegistrationBean = (DelegatingFilterProxyRegistrationBean) initializer;
			filterRegistrationBean.setUrlPatterns(Lists.newArrayList("/admin/*"));
		}
		if (Filter.class == type && order != null) {
			FilterRegistrationBean filterRegistrationBean = (FilterRegistrationBean) initializer;
			filterRegistrationBean.setOrder(order);
		}

		super.addServletContextInitializerBean(type, beanName, initializer, beanFactory, source);

	}

}