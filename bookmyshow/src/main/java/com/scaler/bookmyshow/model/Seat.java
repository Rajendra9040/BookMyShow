package com.scaler.bookmyshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
@Entity
public class Seat extends BaseModel {
    private String seatNumber;
    @ManyToOne
    private Screen screen;
    @ManyToOne
    private SeatType seatType;
    private int rowVal;
    private int colVal;
}
