package com.polystore.polystorebackend.api.requests;


import lombok.Data;

import java.util.Date;

@Data
public class ReviewUpdateRequest {
    String review;
    Date date;
}
