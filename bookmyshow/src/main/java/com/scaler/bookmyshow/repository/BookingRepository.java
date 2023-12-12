package com.scaler.bookmyshow.repository;

import com.scaler.bookmyshow.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
