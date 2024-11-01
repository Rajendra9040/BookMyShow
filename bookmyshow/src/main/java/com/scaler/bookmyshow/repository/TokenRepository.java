package com.scaler.bookmyshow.repository;

import com.scaler.bookmyshow.model.userAuth.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUserId(Long userId);

    @Transactional
    void deleteByUserIdAndValue(Long userId, String value);
}
