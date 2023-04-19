package com.polystore.polystorebackend.api.responses;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.Scene;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.jta.UserTransactionAdapter;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SceneResponse {
    private int sceneId;
    private int productId;
    private String color;
    private int[] cameraPosition;
    private short fov;
    private Boolean autoRotate;
    private String hdri;


    public static SceneResponse sceneToSceneResponse(Scene scene){
        SceneResponse sceneResponse = new SceneResponse();
        sceneResponse.autoRotate = scene.getAutoRotate();
        sceneResponse.fov = scene.getFov();
        sceneResponse.cameraPosition = new int[3];
        sceneResponse.cameraPosition[0] = scene.getCameraX();
        sceneResponse.cameraPosition[1] = scene.getCameraY();
        sceneResponse.cameraPosition[2] = scene.getCameraZ();
        sceneResponse.hdri = scene.getHdriUrl();
        sceneResponse.sceneId = scene.getSceneId();
        sceneResponse.color = scene.getColor();
        sceneResponse.productId = scene.getProduct().getProductId();
        return sceneResponse;
    }

    public static List<SceneResponse> sceneListToSceneResponse(List<Scene> sceneList){
        List<SceneResponse> sceneResponses = new ArrayList<>();
        for (Scene scene:
             sceneList) {
            sceneResponses.add(sceneToSceneResponse(scene));
        }
        return sceneResponses;
    }

}





