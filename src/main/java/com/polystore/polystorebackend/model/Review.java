package com.polystore.polystorebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int previewId;

    @ManyToOne
    @JoinColumn(name = "reviewer",
                referencedColumnName = "username")
    private User reviewer;

    @Id
    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @Column(name = "review")
    private String review;
}
