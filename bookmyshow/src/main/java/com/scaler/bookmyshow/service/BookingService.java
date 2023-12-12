package com.scaler.bookmyshow.service;

import com.scaler.bookmyshow.model.Booking;
import com.scaler.bookmyshow.model.Show;
import com.scaler.bookmyshow.model.ShowSeat;
import com.scaler.bookmyshow.model.User;
import com.scaler.bookmyshow.repository.BookingRepository;
import com.scaler.bookmyshow.repository.ShowRepository;
import com.scaler.bookmyshow.repository.ShowSeatRepository;
import com.scaler.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;

    @Autowired
    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            ShowRepository showRepository,
            ShowSeatRepository showSeatRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(
            Long userId,
            Long showId,
            List<Long> showSeatIds

    ) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not exist!");
        }

        User bookedBy = userOptional.get();
        Optional<Show> showOptional = showRepository.findById(showId);

        if (showOptional.isEmpty()) {
            throw  new RuntimeException("Show not found!");
        }

        Show bookedShow = showOptional.get();

        // Transaction start here
        List<ShowSeat> showSeatsToBeBooked = showSeatRepository.findAllById(showSeatIds);

        return null;
    }
}
