package com.polystore.polystorebackend.model;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="likes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    @EmbeddedId
    private LikesId id;


    @Column(name = "liked")
    private boolean liked;
}
