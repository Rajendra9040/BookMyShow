package com.scaler.bookmyshow.controller;


import com.scaler.bookmyshow.dto.CommonResponse;
import com.scaler.bookmyshow.dto.SignInRequest;
import com.scaler.bookmyshow.dto.SignInResponse;
import com.scaler.bookmyshow.dto.SignOutRequest;
import com.scaler.bookmyshow.dto.SignUpRequest;
import com.scaler.bookmyshow.dto.SignUpResponse;
import com.scaler.bookmyshow.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bms")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
        SignUpResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request) {
        SignInResponse response = authService.signIn(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<CommonResponse> signOut(SignOutRequest request) {
        authService.signOut(request);
        CommonResponse response = new CommonResponse().setMessage("User sign out successfully!");
        return ResponseEntity.ok(response);
    }

}
