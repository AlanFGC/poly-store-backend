package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.api.requests.SceneRequest;
import com.polystore.polystorebackend.api.responses.SceneResponse;
import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.Scene;
import com.polystore.polystorebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scene")
@CrossOrigin
public class SceneController {

    @Autowired
    private ProductService productService;
    @GetMapping("/byProduct/{id}")
    public ResponseEntity<SceneResponse> getSceneByProductId(@PathVariable int id){
        try {
            Scene scene = productService.getSceneByProductId(id);
            return new ResponseEntity<>(SceneResponse.sceneToSceneResponse(scene), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<SceneResponse> createScene(@RequestBody SceneRequest sceneRequest){
        try {
            System.out.println(sceneRequest.toString());

            Scene scene = SceneRequest.sceneRequestToScene(sceneRequest);
            scene = productService.createScene(scene);
            return new ResponseEntity<>(SceneResponse.sceneToSceneResponse(scene), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<SceneResponse> updateScene(@RequestBody SceneRequest sceneRequest){
        try {
            Scene scene = productService.updateScene(SceneRequest.sceneRequestToScene(sceneRequest));
            return new ResponseEntity<>(SceneResponse.sceneToSceneResponse(scene), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deletedScene(@PathVariable int id){
        return "deleteScene";
    }
}
