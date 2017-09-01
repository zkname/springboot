package com.zkname;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.zkname.demo.security.springsecurity.MyAuthenticationFailureHandler;
import com.zkname.demo.security.springsecurity.MyAuthenticationSuccessHandler;
import com.zkname.demo.security.springsecurity.MyLogoutSuccessHandler;
import com.zkname.demo.security.springsecurity.MySecurityFilter;
import com.zkname.demo.security.springsecurity.MyUserDetailService;
import com.zkname.demo.security.springsecurity.TimeoutAuthenticationEntryPoint;

@SpringBootApplication
@Configuration
@ComponentScan("com.zkname")
@PropertySource(encoding="UTF-8", value = { "classpath:applicationContext.properties" })
public class Application extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	   
	@Autowired
	private MySecurityFilter mySecurityFilter;

	@Autowired
	private MyLogoutSuccessHandler myLogoutSuccessHandler;

	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

	@Autowired
	private TimeoutAuthenticationEntryPoint timeoutAuthenticationEntryPoint;
	
	private @Value("${spring.security.login.url}") String springSecurityLoginUrl;
	
	protected void configure(HttpSecurity http) throws Exception {	
		http.addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class).csrf().disable().authorizeRequests()
				.antMatchers("/","/login","/index")
				.permitAll().and().authorizeRequests().antMatchers("/admin/**").authenticated().and().logout()
				.logoutUrl("/admin/logout").logoutSuccessHandler(myLogoutSuccessHandler)
				.invalidateHttpSession(true).and().formLogin().loginPage("/login")
				.loginProcessingUrl(springSecurityLoginUrl)
				.successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailureHandler)
				.usernameParameter("username").passwordParameter("password").and().headers().frameOptions().sameOrigin()
				.and().httpBasic().authenticationEntryPoint(timeoutAuthenticationEntryPoint).and()
				// session管理
				.sessionManagement().sessionFixation().changeSessionId().maximumSessions(1).expiredUrl("/");
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailService();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(new Md5PasswordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		return daoAuthenticationProvider;
	}

	/*
	 * 表达式控制器
	 */
	@Bean(name = "expressionHandler")
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		webSecurityExpressionHandler.setDefaultRolePrefix("A_");
		return webSecurityExpressionHandler;
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication ctx = new SpringApplication(Application.class);
		ApplicationContext applicationContext = ctx.run(args);
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		for (String profile : activeProfiles) {
			logger.info("Spring Boot 使用profile为:{}", profile);
		}
	}

}
