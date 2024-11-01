package com.scaler.bookmyshow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seats")
public class Seat extends BaseModel {
    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "screen_id")
    private Long screenId;

    @Column(name = "seat_type_id")
    private Long seatTypeId;

    @Column(name = "row_value")
    private int rowValue;

    @Column(name = "column_value")
    private int columnValue;
}
