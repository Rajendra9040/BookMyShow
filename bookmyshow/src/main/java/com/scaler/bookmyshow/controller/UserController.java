package com.scaler.bookmyshow.controller;


import com.scaler.bookmyshow.dto.SignUpRequest;
import com.scaler.bookmyshow.mapper.UserMapper;
import com.scaler.bookmyshow.model.User;
import com.scaler.bookmyshow.service.UserService;
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
    private final UserService userService;
    @PostMapping("/demo")
    public ResponseEntity<User> demo(@RequestBody SignUpRequest request) {
        User user = UserMapper.INSTANCE.mapUserFromSignUpRequest(request);
        System.out.println(user);
        userService.sendEmail();
        return ResponseEntity.ok(user);
    }
}
