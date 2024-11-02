package com.scaler.bookmyshow.model;

import com.scaler.bookmyshow.model.enums.ShowSeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "show_seats")
public class ShowSeat extends BaseModel {
    @Column(name = "show_id")
    private Long showId;

    @Column(name = "seat_id")
    private Long seatId;

    @Column(name = "booking_id")
    private Long bookingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "show_seat_status_id")
    private ShowSeatStatus showSeatStatus;

    @Column(name = "locked_at")
    private Date lockedAt;

}
