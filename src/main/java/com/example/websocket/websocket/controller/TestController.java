package com.example.websocket.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TestController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(){
        return "start success";
    }

    @RequestMapping(value = "/getTime",method = RequestMethod.GET)
    public void getTime(){
        this.simpMessagingTemplate.convertAndSend("/topic/notice", formatter.format(LocalDateTime.now()));
    }

    @RequestMapping(value = "testSyncMess/{value}",method = RequestMethod.GET)
    public void testSyncMess(@PathVariable String value){
        WebSocketTestController.users.forEach((name, principal)->{
            simpMessagingTemplate.convertAndSendToUser(principal.getName(),"/topic/chart", value);
        });
    }
}
