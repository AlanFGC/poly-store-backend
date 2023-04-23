package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.model.Scene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SceneRepository extends JpaRepository<Scene, Integer> {



    @Query(value = "select s from Scene s where s.product.productId = :productId")
    public Optional<Scene> getSceneByProductId(@Param("productId") int productId);
}
