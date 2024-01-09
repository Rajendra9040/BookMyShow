package com.scaler.bookmyshow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
@Entity
public class Seat extends BaseModel {
    @Column(name = "seat_number")
    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @ManyToOne
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;

    @Column(name = "row_value")
    private int rowValue;

    @Column(name = "column_value")
    private int columnValue;
}
