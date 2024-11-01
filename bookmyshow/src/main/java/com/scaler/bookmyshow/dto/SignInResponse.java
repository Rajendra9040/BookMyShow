package com.scaler.bookmyshow.dto;

import lombok.Builder;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

@Data
@Builder
public class SignInResponse {
    private String name;
    private String email;
    private String token;
    private String message;
}
