package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.model.Product;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
