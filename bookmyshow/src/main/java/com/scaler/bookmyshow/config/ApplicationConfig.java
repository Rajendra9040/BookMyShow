package com.scaler.bookmyshow.config;

import com.scaler.bookmyshow.model.userAuth.Role;
import com.scaler.bookmyshow.model.userAuth.User;
import com.scaler.bookmyshow.model.userAuth.UserRole;
import com.scaler.bookmyshow.repository.RoleRepository;
import com.scaler.bookmyshow.repository.UserRepository;
import com.scaler.bookmyshow.repository.UserRoleRepository;
import com.scaler.bookmyshow.security.MyCustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository
            .findByEmail(username)
            .map(this::createUserDetails)
            .orElseThrow(()-> new UsernameNotFoundException("Username not found!"));
    }

    public UserDetails createUserDetails(User user) {
        return new MyCustomUserDetails(user, this.getUserRoles(user));
    }

    public List<Role> getUserRoles(User user) {
        List<Long> userRoleIds = userRoleRepository.findByUserId(user.getId()).stream()
            .map(UserRole::getId).toList();
        return roleRepository.findByIdIn(userRoleIds);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
