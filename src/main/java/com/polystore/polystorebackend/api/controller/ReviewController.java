package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.api.responses.ReviewResponse;
import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.Review;
import com.polystore.polystorebackend.repository.ReviewRepository;
import com.polystore.polystorebackend.service.ProductService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
public class ReviewController {


    private final ProductService productService;

    @PostMapping("/post")
    public ResponseEntity<ReviewResponse> postReview(@RequestBody Review review){
        try {
            productService.createtReview(review);

            return new ResponseEntity<>(ReviewResponse.reviewToReviewResponse(review), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{productId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsFromProduct(@PathVariable int productId){
        try {

            return new ResponseEntity<>(
                    ReviewResponse.reviewListToreviewResponseList(productService.getReviewsByProductId(productId)),
                    HttpStatus.OK
            );
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
