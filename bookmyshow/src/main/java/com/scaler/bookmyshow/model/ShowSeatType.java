package com.scaler.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
    @ManyToOne
    private SeatType seatType;
    private int price;
}
