package com.scaler.bookmyshow.repository;

import com.scaler.bookmyshow.model.Show;
import com.scaler.bookmyshow.model.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
    List<ShowSeatType> findAllByShow(Show show);
}
