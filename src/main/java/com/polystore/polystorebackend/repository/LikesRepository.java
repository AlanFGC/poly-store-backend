package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.model.Likes;
import com.polystore.polystorebackend.model.LikesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {
    @Query("update Likes l set l.liked = :state where l.id.userId = :userId AND l.id.productId = :productId")
    void setLikeState(int productId, int userId, boolean state);

    @Query("SELECT l FROM Likes l where l.id.productId.productId = :productId")
    List<Likes> selectLikesBYProductId(@Param("productId") int productId);
}
