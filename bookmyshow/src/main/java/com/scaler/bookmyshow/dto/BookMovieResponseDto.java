package com.scaler.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BookMovieResponseDto {
    private ResponseStatus responseStatus;
    private Long bookingId;
    private int amount;
}
