package com.example.websocket.controller;

import lombok.Data;
import lombok.experimental.Accessors;

import java.security.Principal;

@Data
@Accessors(chain = true)
public class MyPrincipal implements Principal {

    private String myname;

    private String sessionId;

    @Override
    public String getName() {
        // 支持同一个用户多客户端访问
        return myname +"_"+ sessionId;
    }
}
