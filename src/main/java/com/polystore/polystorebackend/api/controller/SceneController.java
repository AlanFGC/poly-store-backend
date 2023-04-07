package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.model.Scene;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scene")
public class SceneController {
    @GetMapping("/{id}")
    public String getScene(@PathVariable int id){
        return "Getting scene" + id;
    }
    @PostMapping("/create")
    public String createScene(@RequestBody Scene scene){
        return "Creating scene....";
    }
    @DeleteMapping("/delete/{id}")
    public String deletedScene(@PathVariable int id){
        return "deleteScene";
    }


}
