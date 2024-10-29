package com.scaler.bookmyshow.service;

import com.scaler.bookmyshow.dto.BookingConfirmationDto;
import com.scaler.bookmyshow.model.User;
import com.scaler.bookmyshow.repository.UserRepository;
import com.scaler.bookmyshow.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public User signUp(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            throw new RuntimeException("User with this email exist!");
        }
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
            .email(email)
            .password(password)
            .build();

        return userRepository.save(user);
    }
    public User logIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not exist!");
        }

        User user = userOptional.get();
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
//            return user;
//        }
        throw new RuntimeException("Password not matched!");
    }

    public void sendEmail() {
        BookingConfirmationDto bookingConfirmationDto = new BookingConfirmationDto()
            .setFrom("rajumahapatra096@gmail.com")
            .setTo("rmahapatra1999@gmail.com")
            .setBody("Hello Rajendra")
            .setSubject("Welcome Message");

        kafkaTemplate.send("sendEmail", ObjectMapperUtils.convertObjectToJson(bookingConfirmationDto));

    }
}
