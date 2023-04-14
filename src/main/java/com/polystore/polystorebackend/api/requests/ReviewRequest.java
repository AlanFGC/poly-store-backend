package com.polystore.polystorebackend.api.requests;


import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.Review;
import com.polystore.polystorebackend.model.ReviewId;
import com.polystore.polystorebackend.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReviewRequest {
    public int productid;
    public String review;
    public Date date;


    public static Review reviewRequestToReview(ReviewRequest reviewRequest, String username){
        Review review = new Review();
        Product product = new Product();
        User user = new User();
        user.setUsername(username);

        review.setReview(reviewRequest.review);
        review.setDate(reviewRequest.date);
        product.setProductId(reviewRequest.productid);
        ReviewId reviewId = new ReviewId();

        reviewId.setProductId(product);
        reviewId.setUsername(user);

        review.setReviewId(reviewId);

        return review;
    }
}
