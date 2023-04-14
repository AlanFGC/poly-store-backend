package com.polystore.polystorebackend.api.requests;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.User;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@NonNull
public class ProductRequest {

    private String username;
    private Date date;
    private String resource;
        private String thumbnail;
    private Double price;

    public static Product convertToProduct(ProductRequest productRequest){

        User user = new User();
        user.setUsername(productRequest.getUsername());
        return Product.builder()
                .likes(0)
                .owner(user)
                .views(0)
                .resourceURL(productRequest.resource)
                .thumbnailURL(productRequest.thumbnail)
                .date(productRequest.date)
                .build();
    }



}
