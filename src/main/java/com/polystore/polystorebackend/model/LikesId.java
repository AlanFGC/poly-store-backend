package com.polystore.polystorebackend.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikesId implements Serializable {
    @ManyToOne
    @JoinColumn(name="product")
    private Product productId;
    @ManyToOne
    @JoinColumn(name="user")
    private User userId;
}
