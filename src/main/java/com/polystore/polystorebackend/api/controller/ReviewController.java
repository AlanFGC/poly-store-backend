package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.api.requests.ReviewRequest;
import com.polystore.polystorebackend.api.responses.ReviewResponse;
import com.polystore.polystorebackend.model.Review;
import com.polystore.polystorebackend.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
@CrossOrigin
public class ReviewController {

    private final ProductService productService;

    @PostMapping("/post")
    public ResponseEntity<ReviewResponse> postReview(Principal principal, @RequestBody ReviewRequest reviewRequest){
        try {
            Review review = productService.createtReview(reviewRequest, principal.getName());
            return new ResponseEntity<>(ReviewResponse.reviewToReviewResponse(review), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
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
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @Transactional
    @PutMapping("/post")
    public ResponseEntity<ReviewResponse> updateReview(Principal principal, @RequestBody ReviewRequest reviewRequest){
        try {
            Review review = productService.updateReview(reviewRequest, principal.getName());
            return new ResponseEntity<>(ReviewResponse.reviewToReviewResponse(review), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ReviewResponse> deleteReviewFromProductUser(@PathVariable int productId, Principal principal){
        try {
            String username = principal.getName();
            productService.deleteReview(username, productId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
