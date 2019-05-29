package com.example.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class WebSocketTestController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @MessageMapping("/getTime")
    public void getTime(){
        this.simpMessagingTemplate.convertAndSend("/topic/notice", formatter.format(LocalDateTime.now()));
    }

    @MessageMapping("/sendToUser")
    public void sendToUser(MyPrincipal principal, Say say){
        System.out.println(principal.getName());
        this.simpMessagingTemplate.convertAndSendToUser(say.getName(),"/chart", say.getValue());
    }
}
