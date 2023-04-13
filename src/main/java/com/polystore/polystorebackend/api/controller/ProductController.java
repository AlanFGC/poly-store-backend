package com.polystore.polystorebackend.api.controller;
import com.polystore.polystorebackend.api.responses.ProductResponse;
import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable int id){
        return ProductResponse.productToProductResponse(productService.findById(id));
    }

    @GetMapping("/list/{n}")
    public List<ProductResponse> getNProducts(@PathVariable int n){
        List<Product> productList = productService.getPageOfSize(n);
        return ProductResponse.listToProductResponse(productList);
    }

    @PostMapping("/create")
    public ProductResponse  createProduct(@RequestBody Product product){
        return ProductResponse.productToProductResponse(productService.createProduct(product));
    }

    @DeleteMapping("/{id}")
    public ProductResponse deleteProduct(@PathVariable int id){
        return ProductResponse.productToProductResponse(productService.deleteProduct(id));
    }

    @PutMapping("/like/{id}")
    public ResponseEntity increaseLike(Principal principal, @PathVariable int id){
        try {
            productService.giveLike(principal.getName(), id);
        } catch (Exception e) {
            return new ResponseEntity<>("Couldn't complete transaction", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Liked changed", HttpStatus.OK);
    }
}