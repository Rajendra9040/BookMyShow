package com.scaler.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SignInRequest {
    private String email;
    private String password;
}
