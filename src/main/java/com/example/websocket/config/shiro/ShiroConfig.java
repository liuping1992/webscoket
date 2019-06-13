package com.example.websocket.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: energy-system
 * @description: shiro 配置
 * @author: Thomas.Yang
 * @create: 2019-03-25 09:52
 **/
@Configuration
public class ShiroConfig {

	// 默认 session 1小时过期
	public static final Long SESSION_TIME_OUT = 3600 * 1000L;

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setLoginUrl("/main_part/user/login");
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/main_part/user/login", "anon");
		filterChainDefinitionMap.put("/main_part/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public DefaultWebSecurityManager securityManager(MyShiroRealm myShiroRealm){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm);
		return securityManager;
	}

	@Bean
	public MyShiroRealm shiroRealm(){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		return myShiroRealm;
	}


}