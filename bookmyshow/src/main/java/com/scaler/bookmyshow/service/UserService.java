package com.scaler.bookmyshow.service;

import com.scaler.bookmyshow.advice.exception.ProgramException;
import com.scaler.bookmyshow.dto.BookingConfirmationDto;
import com.scaler.bookmyshow.dto.SignUpRequest;
import com.scaler.bookmyshow.mapper.UserMapper;
import com.scaler.bookmyshow.model.userAuth.Role;
import com.scaler.bookmyshow.model.userAuth.User;
import com.scaler.bookmyshow.model.userAuth.UserRole;
import com.scaler.bookmyshow.repository.RoleRepository;
import com.scaler.bookmyshow.repository.UserRepository;
import com.scaler.bookmyshow.repository.UserRoleRepository;
import com.scaler.bookmyshow.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

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

    public User createUser(SignUpRequest request) {
        userRepository.findByEmail(request.getEmail())
            .ifPresent(user -> {
                throw new RuntimeException("User with this email already exists!");
            });
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = UserMapper.INSTANCE.mapUserFromSignUpRequest(request);
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new ProgramException("Default role not found in db!"));
        User createUser = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(createUser.getId());
        userRoleRepository.save(userRole);
        return createUser;
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(String.format("User not found for id %s", id)));
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException(String.format("User not found for email %s", email)));
    }

    public User getUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException(String.format("User not found for email %s", email)));
        if (!passwordEncoder.matches(password, user.getHashedPassword())) {
            throw new ProgramException("Invalid password!");
        }
        return user;
    }
}
