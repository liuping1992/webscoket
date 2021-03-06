package com.example.websocket.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebSocketTestController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public static Map<String, MyPrincipal> users = new ConcurrentHashMap<>();

    @MessageMapping("/sendToUser")
    @SendToUser("/topic/chart")
    public String sendToUser(MyPrincipal principal){
        users.put(principal.getName(), principal);
        return "订阅成功";
    }

    @MessageMapping("/sendToOne")
    public void sendToOne(Say say){
        simpMessagingTemplate.convertAndSendToUser(say.getName(), "/topic/chart", say.getValue());
    }

}
