/**
 * Project Name:test-client
 * File Name:RedisConfig.java
 * Package Name:org.test.client.config
 * Date:2018年1月5日上午10:39:24
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.hanren.admin.config.shiro;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * ClassName:RedisConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年1月5日 上午10:39:24 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */

@Configuration
public class ShiroConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.password}")
	private String password;

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
		filters.put("authc", new MyFormAuthenticationFilter());

		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		//filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/subToken", "anon");
		filterChainDefinitionMap.put("/companyLogin", "anon");
		filterChainDefinitionMap.put("/companyRegister", "anon");
		filterChainDefinitionMap.put("/companySubmit", "anon");
		filterChainDefinitionMap.put("/pushEndPoint/**", "anon");
		filterChainDefinitionMap.put("/send", "anon");
		filterChainDefinitionMap.put("/logout", "anon");	
		
		filterChainDefinitionMap.put("/upload/callback", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setLoginUrl("/unauth");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(1);// 散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}

	@Bean
	public GovernmentShiroRealm myShiroRealm() {
		GovernmentShiroRealm myShiroRealm = new GovernmentShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}
	
	
	@Bean
	public EnterpriseShiroRealm enterpriseShiroRealm() {
		EnterpriseShiroRealm enterpriseShiroRealm = new EnterpriseShiroRealm();
		enterpriseShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return enterpriseShiroRealm;
	}
	
	@Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		
		
		securityManager.setAuthenticator(modularRealmAuthenticator());
        List<Realm> realms = new ArrayList<>();
        realms.add(myShiroRealm());
        realms.add(enterpriseShiroRealm());
        securityManager.setRealms(realms);
		//securityManager.setRealm(myShiroRealm());
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		// 自定义缓存实现 使用redis
		securityManager.setCacheManager(redisCacheManager());
		return securityManager;
	}

	@Bean
	public SessionManager sessionManager() {
		MySessionManager mySessionManager = new MySessionManager();
		mySessionManager.setSessionDAO(redisSessionDAO());
		return mySessionManager;
	}

	@Bean
	public RedisCacheManager redisCacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		redisCacheManager.setExpire(18000000);
		return redisCacheManager;
	}

	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		redisSessionDAO.setExpire(18000000);
		return redisSessionDAO;
	}

	@Bean
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setTimeout(timeout);
		redisManager.setPassword(password);
		return redisManager;
	}
	
	/**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
       AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
       authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
       return authorizationAttributeSourceAdvisor;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }
    
	@Bean
	public HandlerExceptionResolver handlerExceptionResolver() {
		return new MyExceptionHandler();
	}

}
