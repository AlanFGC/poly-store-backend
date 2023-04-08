package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.model.Review;
import com.polystore.polystorebackend.model.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    @Query(value = "SELECT r from Review r where  r.reviewId.productId = :productId")
    public List<Review> findByProductId(int productId);
}
