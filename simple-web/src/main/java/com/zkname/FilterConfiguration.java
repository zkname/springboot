package com.zkname;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zkname.patchca.PatchcaFilter;

/**
 * 自定义Filter
 * @author ZhangKai
 *
 */
@Configuration
public class FilterConfiguration {

	@Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new PatchcaFilter());
        registration.addUrlPatterns("/images/code.png");
        registration.setName("patchcaFilter");
        registration.setOrder(1);
        return registration;
    }
}