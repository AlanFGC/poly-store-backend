package com.polystore.polystorebackend.api.responses;


import com.polystore.polystorebackend.model.Review;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class ReviewResponse {

    private int productId;
    private String username;
    private String review;
    private Date date;


    public static ReviewResponse reviewToReviewResponse(Review review){
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.date = review.getDate();
        reviewResponse.review = review.getReview();
        reviewResponse.productId = review.getReviewId().getProductId().getProductId();
        reviewResponse.username = review.getReviewId().getUsername().getUsername();
        return reviewResponse;
    }


    public static List<ReviewResponse> reviewListToreviewResponseList(List<Review> reviews){
        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for (Review review:
             reviews) {
            reviewResponseList.add(reviewToReviewResponse(review));
        }
        return reviewResponseList;
    }
}
