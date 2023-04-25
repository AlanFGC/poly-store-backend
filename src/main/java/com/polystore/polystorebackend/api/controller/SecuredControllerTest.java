package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.model.User;
import com.polystore.polystorebackend.repository.ProductRepository;
import com.polystore.polystorebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/secured")
@CrossOrigin
public class SecuredControllerTest {
    @Autowired
    UserService userService;
    @GetMapping("/hello")
    public String getHello() {
        return "Hello this is your info \n";
    }

    @GetMapping("/saymyname")
    public String getMyName(Principal principal){
        return principal.getName() + "\n -You are goddamn right!";
    }
    @GetMapping("/saymydetails")
    public User getHello(Principal principal) {
        return userService.getUserByName(principal.getName());
    }
}
