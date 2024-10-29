package com.scaler.bookmyshow.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponse {
    private String token;
    private String message;
}
