package com.scaler.bookmyshow.service;

import com.scaler.bookmyshow.config.ApplicationConfig;
import com.scaler.bookmyshow.dto.SignInRequest;
import com.scaler.bookmyshow.dto.SignInResponse;
import com.scaler.bookmyshow.dto.SignOutRequest;
import com.scaler.bookmyshow.dto.SignUpRequest;
import com.scaler.bookmyshow.dto.SignUpResponse;
import com.scaler.bookmyshow.model.userAuth.Token;
import com.scaler.bookmyshow.model.userAuth.User;
import com.scaler.bookmyshow.repository.TokenRepository;
import com.scaler.bookmyshow.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final ApplicationConfig applicationConfig;
    private final TokenRepository tokenRepository;

    public SignUpResponse register(SignUpRequest request) {
        User user = userService.createUser(request);
        String jwtToken = jwtService.generateToken(applicationConfig.createUserDetails(user));
        saveToken(user.getId(), jwtToken);
        return SignUpResponse.builder()
            .email(user.getEmail())
            .token(jwtToken)
            .message("User registered successfully!")
            .build();
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userService.getUser(signInRequest.getEmail(), signInRequest.getPassword());
        String jwtToken = jwtService.generateToken(applicationConfig.createUserDetails(user));
        saveToken(user.getId(), jwtToken);
        return SignInResponse.builder()
            .email(user.getEmail())
            .token(jwtToken)
            .message("User signed in successfully!")
            .build();
    }

    public void signOut(SignOutRequest signOutRequest) {
        User user = userService.getUser(signOutRequest.getEmail());
        tokenRepository.deleteByUserIdAndValue(user.getId(), signOutRequest.getToken());
    }

    private void saveToken(Long userId, String jwtToken) {
        Token token = new Token();
        token.setUserId(userId);
        token.setValue(jwtToken);
        tokenRepository.save(token);
    }
}
