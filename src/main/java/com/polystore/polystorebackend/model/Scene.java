package com.polystore.polystorebackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name="scene")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scene {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int sceneId;
    @OneToOne
    @JoinColumn(name = "productId", unique = true)
    private Product product;
    @Column(name="color", length = 7)
    private String color;
    private int cameraX;
    private int cameraY;
    private int cameraZ;
    @Column(name="fov")
    private short fov;
    @Column(name="auto_rotate")
    private Boolean autoRotate;
    @Column(name = "hdri_url")
    private String hdriUrl;
}
