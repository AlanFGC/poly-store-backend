package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.model.Product;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select p from Product p ORDER BY p.productId desc")
    List<Product> getLatest(Pageable pageable);

    @Query(value = "select p from Product p where p.owner.username = :username order by p.date ASC")
    List<Product> getProductsByUser(String username);

    @Query(value = "select p from Product p where p.title LIKE %:keyword% or p.description LIKE %:keyword% or p.owner.username LIKE %:keyword% order by p.date asc limit 25")
    List<Product> getProductsByKeyword(String keyword);
}
