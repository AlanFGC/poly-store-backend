package com.polystore.polystorebackend.api.requests;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.User;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@NonNull
public class ProductRequest {

    private Date date;
    private String resource;
    private String thumbnail;
    private Double price;
    private String title;
    private String description;

    public static Product convertToProduct(ProductRequest productRequest,String username) {

        User user = new User();
        user.setUsername(username);
        return Product.builder()
                .likes(0)
                .price(productRequest.price)
                .owner(user)
                .views(0)
                .resourceURL(productRequest.resource)
                .thumbnailURL(productRequest.thumbnail)
                .date(productRequest.date)
                .description(productRequest.description)
                .title(productRequest.title)
                .build();
    }

}
