package com.polystore.polystorebackend.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name="likes")
@AllArgsConstructor
@NoArgsConstructor
public class Likes {

    @EmbeddedId
    private LikesId id;

    private boolean liked;
}
