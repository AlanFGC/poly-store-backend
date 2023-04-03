package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String name);
}