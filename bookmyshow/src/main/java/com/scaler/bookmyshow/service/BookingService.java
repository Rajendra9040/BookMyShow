package com.scaler.bookmyshow.service;

import com.scaler.bookmyshow.advice.exception.ProgramException;
import com.scaler.bookmyshow.dto.BookMovieRequestDto;
import com.scaler.bookmyshow.model.Booking;
import com.scaler.bookmyshow.model.Show;
import com.scaler.bookmyshow.model.ShowSeat;
import com.scaler.bookmyshow.model.enums.BookingStatus;
import com.scaler.bookmyshow.model.enums.ShowSeatStatus;
import com.scaler.bookmyshow.model.userAuth.User;
import com.scaler.bookmyshow.repository.BookingRepository;
import com.scaler.bookmyshow.repository.ShowRepository;
import com.scaler.bookmyshow.repository.ShowSeatRepository;
import com.scaler.bookmyshow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final PriceCalculator priceCalculator;

    public Booking bookMovie(BookMovieRequestDto bookMovieRequestDto) {
        Long userId = bookMovieRequestDto.getUserId();
        Long showId = bookMovieRequestDto.getShowId();
        List<Long> showSeatIds = bookMovieRequestDto.getShowSeatIds();
        User user = userRepository.findById(userId).orElseThrow(()-> new ProgramException(String.format("User with id: %s not present!", userId)));
        Show show = showRepository.findById(showId).orElseThrow(()-> new ProgramException(String.format("Show with id: %s not present!", showId)));

        // Transaction start here
        List<ShowSeat> bookedShowSeats = bookSeats(showSeatIds);
        // release lock here

        Booking booking = new Booking();
        booking.setUserId(user.getId());
        booking.setBookedAt(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(priceCalculator.calculatePrice(bookedShowSeats, show));
        Booking finalBooking = bookingRepository.save(booking);;
        bookedShowSeats.forEach(bss -> bss.setBookingId(finalBooking.getId()));
        showSeatRepository.saveAll(bookedShowSeats);
        //ToDo: save and update the Payments here
        return finalBooking;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<ShowSeat> bookSeats(List<Long> showSeatIds) {
        List<ShowSeat> showSeatsToBeBooked = showSeatRepository.findAllById(showSeatIds);
        for (ShowSeat showSeat: showSeatsToBeBooked) {
            if (!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                    Duration.between(showSeat.getLockedAt().toInstant(), new Date().toInstant()).toMinutes()>15))
            ) {
                throw new ProgramException("Selected seats are not available!");
            }
        }
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for (ShowSeat showSeat: showSeatsToBeBooked) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }
        return savedShowSeats;
    }
}
