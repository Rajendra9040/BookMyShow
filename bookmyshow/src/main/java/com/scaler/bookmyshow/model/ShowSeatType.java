package com.scaler.bookmyshow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "show_seat_types")
public class ShowSeatType extends BaseModel{
    @Column(name = "show_id")
    private Long showId;

    @Column(name = "seat_type_id")
    private Long seatTypeId;

    @Column(name = "price")
    private int price;
}
