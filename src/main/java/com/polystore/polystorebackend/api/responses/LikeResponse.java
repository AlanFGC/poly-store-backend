package com.polystore.polystorebackend.api.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {
    private boolean state;
    private int productid;
    private int numberOfLikes;
}
