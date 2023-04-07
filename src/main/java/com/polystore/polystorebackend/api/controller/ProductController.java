package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.model.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @GetMapping("/{id}")
    public String getProduct(@PathVariable int id){
        return "getting product" + id;
    }
    @PostMapping("/create")
    public String  createProduct(){
        return "Success";
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id){
        return "deleting product" + id;
    }

}
