package com.polystore.polystorebackend.api.controller;
import com.polystore.polystorebackend.api.responses.LikeResponse;
import com.polystore.polystorebackend.api.requests.ProductRequest;
import com.polystore.polystorebackend.api.responses.ProductResponse;
import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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
    public ResponseEntity<ProductResponse> getProduct(@PathVariable int id){
        try {
            ProductResponse response = ProductResponse.productToProductResponse(productService.findProductById(id));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("keyword={word}")
    public ResponseEntity<List<ProductResponse>> searchProduct(@PathVariable("word") String word){
        List<ProductResponse> response = ProductResponse.listToProductResponse(productService.searchProducts(word));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list/{n}")
    public List<ProductResponse> getNProducts(@PathVariable int n){
        List<Product> productList = productService.getPageOfSize(n);
        return ProductResponse.listToProductResponse(productList);
    }


    @GetMapping("/listbyuser/{username}")
    public List<ProductResponse> getProductsByUsername(@PathVariable String username){
        return ProductResponse.listToProductResponse(productService.getProductsFromUsername(username));
    }

    @PostMapping("/create")
    public ProductResponse  createProduct(@RequestBody ProductRequest productRequest){
        Product product = ProductRequest.convertToProduct(productRequest);
        try {
            return ProductResponse.productToProductResponse(productService.createProduct(product));
        } catch (Exception e) {
            return ProductResponse.productToProductResponse(new Product());
        }
    }

    @DeleteMapping("/{id}")
    public ProductResponse deleteProduct(@PathVariable int id){
        return ProductResponse.productToProductResponse(productService.deleteProduct(id));
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<LikeResponse> increaseLike(Principal principal, @PathVariable int id){

        if (principal.getName() == null){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        try {
            Pair<Integer, Boolean> data = productService.giveLike(principal.getName(), id);
            LikeResponse likeResponse = LikeResponse.builder().numberOfLikes(data.getFirst()).productid(id).state(data.getSecond()).build();
            return new ResponseEntity(likeResponse , HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

}