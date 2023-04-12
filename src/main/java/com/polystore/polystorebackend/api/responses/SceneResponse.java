package com.polystore.polystorebackend.api.responses;


import com.polystore.polystorebackend.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.jta.UserTransactionAdapter;


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
    private String hdriUrl;
}





