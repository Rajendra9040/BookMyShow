package com.scaler.bookmyshow.repository;

import com.scaler.bookmyshow.model.userAuth.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUserId(Long userId);

    @Transactional
    void deleteByUserIdAndValue(Long userId, String value);
}
