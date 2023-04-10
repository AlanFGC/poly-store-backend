package com.polystore.polystorebackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="scene")
@Data
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scene {
    @Id
    private int sceneId;
    @OneToOne
    @PrimaryKeyJoinColumn(name="product_id")
    private Product product;
    @Column(name="color", length = 7)
    private String color;
    @Column(name="camera_pos", length = 3)
    private int[] cameraPosition;
    @Column(name="fov")
    private short fov;
    @Column(name="auto_rotate")
    private Boolean autoRotate;
    @Column(name = "hdri_url")
    private String hdriUrl;
}
