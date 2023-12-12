package com.scaler.bookmyshow.repository;

import com.scaler.bookmyshow.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}
