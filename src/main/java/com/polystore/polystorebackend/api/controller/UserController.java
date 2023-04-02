package com.polystore.polystorebackend.api.controller;

import com.polystore.polystorebackend.api.model.User;
import com.polystore.polystorebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User addUser(@RequestBody User user){
        return userService.createUser(user);
    }


    @PostMapping("/signups")
    public List<User> addUser(@RequestBody List<User> user){
        return userService.createUser(user);
    }


    @GetMapping("/user/id/{id}")
    public User findUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @GetMapping("/user/name/{name}")
    public User findUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }


    @GetMapping("/user/all")
    public List<User> findAllUsers(){
        return userService.findAll();
    }


    @PutMapping("/update-user")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete-user/{id}")
    public  String deleteProduct(@PathVariable int id){
        return userService.deleteById(id);
    }


    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }



}
