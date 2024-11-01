package com.scaler.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SignOutRequest {
    private String email;
    private String token;
}
