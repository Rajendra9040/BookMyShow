package com.scaler.bookmyshow.dto;

import com.scaler.bookmyshow.model.Show;
import com.scaler.bookmyshow.model.ShowSeat;
import com.scaler.bookmyshow.model.User;

import java.util.List;

public class BookMovieRequestDto {
    private Long userId;
    private List<Long> showSeatIds;
    private Long showId;
}
