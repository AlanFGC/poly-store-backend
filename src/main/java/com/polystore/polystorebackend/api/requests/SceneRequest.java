package com.polystore.polystorebackend.api.requests;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.Scene;
import lombok.Data;

import java.security.InvalidParameterException;

@Data
public class SceneRequest {

    private int productId;
    private String color;
    private int[] cameraPosition;
    private short fov;
    private boolean autoRotate;
    private String hdr;

    public static Scene sceneRequestToScene(SceneRequest sceneRequest){
        if (sceneRequest.cameraPosition.length != 3) throw new InvalidParameterException();
        Scene scene = new Scene();
        scene.setCameraX(sceneRequest.cameraPosition[0]);
        scene.setCameraY(sceneRequest.cameraPosition[1]);
        scene.setCameraZ(sceneRequest.cameraPosition[2]);
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
