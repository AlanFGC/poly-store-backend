package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.model.Review;
import com.polystore.polystorebackend.model.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    @Query(value = "SELECT r from Review r where  r.reviewId.productId.productId = :productId")
    List<Review> findByProductId(@Param("productId") int productId);

    @Query("DELETE FROM Review r where r.reviewId.productId = :productId")
    void deleteAllByProductId(int productId);
}