package com.polystore.polystorebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "review")
@Data
public class Review {
    @EmbeddedId
    private ReviewId reviewId;
    @Column(name = "review")
    private String review;
    private Date date;

}
