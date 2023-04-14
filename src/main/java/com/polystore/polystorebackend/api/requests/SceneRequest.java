package com.polystore.polystorebackend.api.requests;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.Scene;
import lombok.Data;

@Data
public class SceneRequest {

    private int productId;
    private String username;
    private String color;
    private int[] cameraPosition;
    private short fov;
    private boolean autoRotate;
    private String hdri;

    public static Scene sceneRequestToScene(SceneRequest sceneRequest){
        Scene scene = new Scene();
        scene.setCameraPosition(sceneRequest.getCameraPosition());
        scene.setFov(sceneRequest.fov);
        scene.setAutoRotate(sceneRequest.autoRotate);
        scene.setColor(sceneRequest.color);
        scene.setHdriUrl(sceneRequest.hdri);
        Product product = new Product();
        product.setProductId(sceneRequest.productId);
        scene.setProduct(product);
        return scene;
    }



}
