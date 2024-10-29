package com.scaler.bookmyshow.dto;

import com.scaler.bookmyshow.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SignUpRequest {
    private String name;
    private String email;
    private String password;

    public static User toUser (SignUpRequest request) {
        return User
            .builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .name(request.getName())
            .build();
    }
}
