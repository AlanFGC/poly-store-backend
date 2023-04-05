package com.polystore.polystorebackend.repository;

import com.polystore.polystorebackend.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {

    @Query(value = "select token from Token where user = :id and (expired = false or revoked = false)")
    List<Token> findAllValidTokenByUser(Integer id);
    Optional<Token> findByToken(String token);
}
