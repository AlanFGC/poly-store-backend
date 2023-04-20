package com.polystore.polystorebackend.api.responses;


import com.polystore.polystorebackend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private int productId;
    private int likes;
    private double price;
    private int views;
    private String owner;
    private String resourceURL;
    private String thumbnailURL;
    private Date date;
    private String description;
    private String title;
    public static ProductResponse productToProductResponse(Product product) {
        if(product == null){
            return new ProductResponse();
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.productId = product.getProductId();
        productResponse.likes = product.getLikes();
        productResponse.price = product.getPrice();
        productResponse.views = product.getViews();
        productResponse.owner = product.getOwner().getUsername();
        productResponse.resourceURL = product.getResourceURL();
        productResponse.thumbnailURL = product.getThumbnailURL();
        productResponse.date = product.getDate();
        productResponse.title = product.getTitle();
        productResponse.description = product.getDescription();
        return productResponse;
    }

    public static List<ProductResponse> listToProductResponse(List<Product> productList) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product:
             productList) {
            productResponses.add(productToProductResponse(product));
        }
        return productResponses;
    }
}
