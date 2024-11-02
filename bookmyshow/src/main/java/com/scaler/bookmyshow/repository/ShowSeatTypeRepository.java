package com.scaler.bookmyshow.repository;

import com.scaler.bookmyshow.model.Show;
import com.scaler.bookmyshow.model.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
    List<ShowSeatType> findAllByShowId(Long showId);
}
