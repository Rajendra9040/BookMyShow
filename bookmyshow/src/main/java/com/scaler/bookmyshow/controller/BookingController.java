package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dto.BookMovieRequestDto;
import com.scaler.bookmyshow.dto.BookMovieResponseDto;
import com.scaler.bookmyshow.dto.ResponseStatus;
import com.scaler.bookmyshow.model.Booking;
import com.scaler.bookmyshow.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("booking")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping()
    public BookMovieResponseDto bookMovie(@RequestBody BookMovieRequestDto bookMovieRequestDto) {
        Long userId = bookMovieRequestDto.getUserId();
        Long showId = bookMovieRequestDto.getShowId();
        List<Long> showSeatIds = bookMovieRequestDto.getShowSeatIds();

        BookMovieResponseDto bookMovieResponseDto = new BookMovieResponseDto();

        try {
            Booking booking = bookingService.bookMovie(userId, showId, showSeatIds);
            bookMovieResponseDto.setBookingId(booking.getId());
            bookMovieResponseDto.setAmount(booking.getAmount());
            bookMovieResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            bookMovieResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return bookMovieResponseDto;
    }
}
