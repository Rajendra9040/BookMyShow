package com.scaler.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class ShowSeat extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "show_seat_status_id")
    private ShowSeatStatus showSeatStatus;

    @Column(name = "locked_at")
    private Date lockedAt;
}
