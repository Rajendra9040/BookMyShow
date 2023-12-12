package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dto.BookMovieRequestDto;
import com.scaler.bookmyshow.dto.BookMovieResponseDto;
import com.scaler.bookmyshow.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDto bookMovie(BookMovieRequestDto bookMovieRequestDto) {
        return null;
    }
}
