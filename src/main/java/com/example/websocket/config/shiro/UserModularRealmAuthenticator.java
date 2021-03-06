package com.example.websocket.config.shiro;


import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {
	private static final Logger logger = LoggerFactory.getLogger(UserModularRealmAuthenticator.class);

	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) {
		logger.info("UserModularRealmAuthenticator:method doAuthenticate() execute ");
		// 判断getRealms()是否返回为空
		assertRealmsConfigured();
		// 强制转换回自定义的CustomizedToken
		UserToken userToken = (UserToken) authenticationToken;
		String loginType = userToken.getLoginType();
		Collection<Realm> realms = getRealms();
		Collection<Realm> typeRealms = new ArrayList<>();
		for (Realm realm : realms) {
            if (realm.getName().contains(loginType))
                typeRealms.add(realm);
        }
		
		// 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            logger.info("doSingleRealmAuthentication() execute ");
            return doSingleRealmAuthentication(((ArrayList<Realm>) typeRealms).get(0), userToken);
        }
        else{
            logger.info("doMultiRealmAuthentication() execute ");
            return doMultiRealmAuthentication(typeRealms, userToken);
        }

	}

}
