package com.polystore.polystorebackend.api.controller;


import netscape.javascript.JSObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secured")
public class SecuredControllerTest {


    @GetMapping("/hello")
    public String getHello(@RequestBody JSObject object){
        return "Hello this is your info \n" + object.toString();
    }
}
