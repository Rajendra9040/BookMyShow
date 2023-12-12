package com.scaler.bookmyshow.repository;

import com.scaler.bookmyshow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
