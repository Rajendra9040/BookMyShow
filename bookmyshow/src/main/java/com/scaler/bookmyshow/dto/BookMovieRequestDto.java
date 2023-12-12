package com.scaler.bookmyshow.dto;

import com.scaler.bookmyshow.model.Show;
import com.scaler.bookmyshow.model.ShowSeat;
import com.scaler.bookmyshow.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookMovieRequestDto {
    private Long userId;
    private List<Long> showSeatIds;
    private Long showId;
}
