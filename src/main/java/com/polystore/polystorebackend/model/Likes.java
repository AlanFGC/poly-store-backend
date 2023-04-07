package com.polystore.polystorebackend.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="likes")
public class Likes {

    @EmbeddedId
    private LikesId id;

    private boolean liked;
}
