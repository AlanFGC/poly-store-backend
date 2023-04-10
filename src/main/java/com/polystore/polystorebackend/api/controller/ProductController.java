package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.service.ProductService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/{id}")
    public String getProduct(@PathVariable int id){
        return "getting product" + id;
    }

    @GetMapping("/list/{n}")
    public List<Product> getNProducts(@PathVariable int n){
        return productService.getNRandomProducts(n);
    }

    @PostMapping("/create")
    public Product  createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable int id){
        return productService.deleteProduct(id);
    }

    @PutMapping("/like/{id}")
    public String increaseLike(Principal principal, @PathVariable int id){

        return principal.getName();
    }

}