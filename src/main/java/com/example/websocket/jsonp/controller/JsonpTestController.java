package com.example.websocket.jsonp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonpTestController {

    @RequestMapping(value = "jsonpTest",method = RequestMethod.GET)
    public String test(@RequestParam(value = "callname",required = false) String callname){
        if (null != callname) {
            return callname + "(" + "{\"name\":\"lp\",\"age\":\"18\"}" + ")";
        } else {
            return "{\"name\":\"lp\",\"age\":\"18\"}";
        }
    }

}
