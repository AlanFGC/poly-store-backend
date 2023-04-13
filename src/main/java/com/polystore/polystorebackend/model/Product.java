package com.polystore.polystorebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int productId;

    private int likes;
    private double price;
    private int views;

    @ManyToOne
    @JoinColumn(name = "owner",
    referencedColumnName = "username")
    private User owner;

    @Column(name = "resource")
    private String resourceURL;

    @Column(name = "thumbnail")
    private String thumbnailURL;

    private Date date;
}
