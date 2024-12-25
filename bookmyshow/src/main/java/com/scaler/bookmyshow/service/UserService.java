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
import com.scaler.bookmyshow.utils.BMSConstant;
import com.scaler.bookmyshow.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    public void sendEmail() {
        BookingConfirmationDto bookingConfirmationDto = new BookingConfirmationDto()
            .setFrom("rajumahapatra096@gmail.com")
            .setTo("rmahapatra1999@gmail.com")
            .setBody("Hello Rajendra")
            .setSubject("Welcome Message");
        kafkaTemplate.send(BMSConstant.SEND_EMAIL, ObjectMapperUtils.convertObjectToJson(bookingConfirmationDto));
    }

    public User createUser(SignUpRequest request) {
        userRepository.findByEmail(request.getEmail())
            .ifPresent(user -> {
                throw new RuntimeException("User with this email already exists!");
            });
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = UserMapper.INSTANCE.mapUserFromSignUpRequest(request);
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new ProgramException("Default role not found in db!"));
        User createUser = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(createUser.getId());
        userRoleRepository.save(userRole);
        return createUser;
    }

    @Cacheable(value = "users", key = "#id")
    public User getUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(String.format("User not found for id %s", id)));
    }

    @Cacheable(value = "users", key = "#email")
    public User getUser(String email) {
//        User user = (User) redisTemplate.opsForHash().get("USER", "USER_" + email);
//        if (Objects.nonNull(user)) {
//            return user;
//        }
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException(String.format("User not found for email %s", email)));
//        redisTemplate.opsForHash().put("USER", "USER_" + email, user);
//        return user;
    }

    public User getUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ProgramException(String.format("User not found for email %s", email)));
        if (!passwordEncoder.matches(password, user.getHashedPassword())) {
            throw new ProgramException("Invalid password!");
        }
        return user;
    }
}
