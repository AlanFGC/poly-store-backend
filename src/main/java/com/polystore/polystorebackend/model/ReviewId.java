package com.polystore.polystorebackend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;



@Embeddable
@Data
public class ReviewId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product")
    private Product productId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user",
    referencedColumnName = "username")
    private User username;
    public ReviewId() {

    }
}
