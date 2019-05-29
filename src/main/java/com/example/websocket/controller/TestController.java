package com.example.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TestController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "test")
    public String test(){
        return "start success";
    }

    @RequestMapping("/getTime")
    public void getTime(){
        this.simpMessagingTemplate.convertAndSend("/topic/notice", formatter.format(LocalDateTime.now()));
    }

    @RequestMapping(value = "testSyncMess/{value}")
    public void testSyncMess(@PathVariable String value){
        WebSocketTestController.users.forEach((name, principal)->{
            simpMessagingTemplate.convertAndSendToUser(principal.getName(),"/topic/chart", value);
        });
    }
}
