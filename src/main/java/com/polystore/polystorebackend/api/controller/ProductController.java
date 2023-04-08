package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/{id}")
    public String getProduct(@PathVariable int id){
        return "getting product" + id;
    }

    @GetMapping("/list/{n}")
    public List<Product> getNProducts(@PathVariable int n){
        return List.of((Product) productService.getNRandomProducts(n));
    }

    @PostMapping("/create")
    public String  createProduct(@RequestBody Product product){
        return "Success";
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id){
        return "deleting product" + id;
    }

}