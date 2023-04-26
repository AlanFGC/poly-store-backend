package com.polystore.polystorebackend.api.requests;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.Scene;
import lombok.Data;

import java.security.InvalidParameterException;

@Data
public class SceneRequest {

    private int productId;
    private String color;
    private int cameraPositionX;
    private int cameraPositionY;
    private int cameraPositionZ;
    private short fov;
    private boolean autoRotate;
    private String hdr;

    public static Scene sceneRequestToScene(SceneRequest sceneRequest){
        Scene scene = new Scene();
        scene.setCameraX(sceneRequest.cameraPositionX);
        scene.setCameraY(sceneRequest.cameraPositionY);
        scene.setCameraZ(sceneRequest.cameraPositionZ);
        scene.setFov(sceneRequest.fov);
        scene.setAutoRotate(sceneRequest.autoRotate);
        scene.setColor(sceneRequest.color);
        scene.setHdriUrl(sceneRequest.hdr);
        Product product = new Product();
        product.setProductId(sceneRequest.productId);
        scene.setProduct(product);
        return scene;
    }



}
