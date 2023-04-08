package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.model.Likes;
import com.polystore.polystorebackend.model.LikesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {
}
