package com.example.websocket.websocket;

import com.example.websocket.websocket.controller.MyPrincipal;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: web-monitor
 * @description: 通道拦截器
 * @author: Thomas.Yang
 * @create: 2018-10-08 10:18
 **/
public class MyChannelInterceptor implements ChannelInterceptor {

    /**
     * 对连接事件进行拦截，获取用户信息
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) {
            return null;
        }
        if (null == accessor.getCommand()) {
            return null;
        }
        //判断客户端发出的命令是不是CONNECT
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> header = accessor.getNativeHeader("myname");
            if (CollectionUtils.isEmpty(header)) {
                return null;
            }
            String name = header.get(0);
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            accessor.setUser(new MyPrincipal().setMyname(name).setSessionId(accessor.getSessionId()));
        }
        return message;
    }
}


