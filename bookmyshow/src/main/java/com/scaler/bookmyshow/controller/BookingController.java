package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dto.BookMovieRequestDto;
import com.scaler.bookmyshow.dto.BookMovieResponseDto;
import com.scaler.bookmyshow.dto.ResponseStatus;
import com.scaler.bookmyshow.model.Booking;
import com.scaler.bookmyshow.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bms/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookMovieResponseDto> bookMovie(@RequestBody BookMovieRequestDto bookMovieRequestDto) {
        Booking booking = bookingService.bookMovie(bookMovieRequestDto);
        BookMovieResponseDto bookMovieResponseDto = new BookMovieResponseDto()
            .setBookingId(booking.getId())
            .setAmount(booking.getAmount())
            .setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(bookMovieResponseDto);
    }
}
