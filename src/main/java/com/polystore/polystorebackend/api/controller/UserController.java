package com.polystore.polystorebackend.api.controller;

import com.polystore.polystorebackend.api.requests.UserModifyRequest;
import com.polystore.polystorebackend.api.responses.UserDetailsResponse;
import com.polystore.polystorebackend.model.User;
import com.polystore.polystorebackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Transactional
    @PutMapping("/edit")
    public ResponseEntity<?> editUser(Principal principal, @RequestBody UserModifyRequest request) {

        try {
            String username = principal.getName();
            User user = userService.edit(username, request);
            UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
            userDetailsResponse.setEmail(user.getEmail());
            userDetailsResponse.setUsername(user.getUsername());
            return new ResponseEntity<>(userDetailsResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //    @Autowired
//    private UserService userService;
//
//    @PostMapping("/signup")
//    public User addUser(@RequestBody User user){
//        return userService.createUser(user);
//    }
//
//
//    @PostMapping("/signups")
//    public List<User> addUser(@RequestBody List<User> user){
//        return userService.createUser(user);
//    }
//
//
//    @GetMapping("/user/id/{id}")
//    public User findUserById(@PathVariable int id){
//        return userService.getUserById(id);
//    }
//
//    @GetMapping("/user/name/{name}")
//    public User findUserByName(@PathVariable String name){
//        return userService.getUserByName(name);
//    }
//
//
//    @GetMapping("/user/all")
//    public List<User> findAllUsers(){
//        return userService.findAll();
//    }
//
//
//
//    @Transactional
//    @PutMapping("/update-user")
//    public User updateUser(@RequestBody User user){
//        return userService.updateUser(user);
//    }
//
//    @DeleteMapping("/delete-user/{id}")
//    public  String deleteProduct(@PathVariable int id){
//        return userService.deleteById(id);
//    }
//
//
//    @GetMapping("/hello")
//    public String sayHello(){
//        return "Hello World";
//    }


}
