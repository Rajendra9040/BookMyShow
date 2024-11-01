package com.scaler.bookmyshow.dto;

import com.scaler.bookmyshow.model.userAuth.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Accessors(chain = true)
public class SignUpRequest {
    private String name;
    private String email;
    private String password;

    public static User toUser (SignUpRequest request, PasswordEncoder passwordEncoder) {
        return User
            .builder()
            .email(request.getEmail())
            .hashedPassword(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .build();
    }
}
