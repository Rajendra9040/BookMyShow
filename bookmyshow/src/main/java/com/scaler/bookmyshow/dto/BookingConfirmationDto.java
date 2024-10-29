package com.scaler.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BookingConfirmationDto {
    private String to;
    private String from;
    private String subject;
    private String body;
}
