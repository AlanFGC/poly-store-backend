package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u where u.username = :name")
    Optional<User> findByUsername(String name);
}