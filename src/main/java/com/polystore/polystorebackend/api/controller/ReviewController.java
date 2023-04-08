package com.polystore.polystorebackend.api.controller;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.Review;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @GetMapping("/reviews/{id}")
    public Review getReviewsByProductId(@PathVariable int productId){
        return new Review();
    }

    @PostMapping("/review")
    public Review postReview(@RequestBody Review review){
        return new Review();
    }
}
