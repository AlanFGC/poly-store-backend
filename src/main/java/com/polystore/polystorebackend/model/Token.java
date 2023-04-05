package com.polystore.polystorebackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;

import java.time.chrono.IsoChronology;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer token_id;
    @Column(unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    private boolean revoked;
    private boolean expired;
    @ManyToOne
    @JoinColumn(name="user")
    private User user;

}
