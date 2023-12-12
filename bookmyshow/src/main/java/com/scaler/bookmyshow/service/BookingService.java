package com.scaler.bookmyshow.service;

import com.scaler.bookmyshow.model.*;
import com.scaler.bookmyshow.repository.BookingRepository;
import com.scaler.bookmyshow.repository.ShowRepository;
import com.scaler.bookmyshow.repository.ShowSeatRepository;
import com.scaler.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final PriceCalculator priceCalculator;

    @Autowired
    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            ShowRepository showRepository,
            ShowSeatRepository showSeatRepository,
            PriceCalculator priceCalculator
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculator = priceCalculator;
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

        for (ShowSeat showSeat: showSeatsToBeBooked) {
            if (!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                Duration.between(showSeat.getLockedAt().toInstant(), new Date().toInstant()).toMinutes()>15)
            ) {
                throw new RuntimeException("Selected seats are not available!");
            }
        }

        List<ShowSeat> savedShowSeats = new ArrayList<>();

        for (ShowSeat showSeat: showSeatsToBeBooked) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        // release lock here
        Booking booking = new Booking();
        booking.setUser(bookedBy);
        booking.setShowSeats(savedShowSeats);
        booking.setBookedAt(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPayments(new ArrayList<>());
        booking.setAmount(priceCalculator.calculatePrice(savedShowSeats, bookedShow));
        booking.setShow(bookedShow);

        booking = bookingRepository.save(booking);

        return booking;
    }
}
