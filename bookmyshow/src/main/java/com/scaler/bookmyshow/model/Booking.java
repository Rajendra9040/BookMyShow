package com.scaler.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking extends BaseModel{
    @ManyToOne
    private User user;
    @ManyToMany
    private List<ShowSeat> showSeats;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @OneToMany
    private List<Payment> payments;
    @ManyToOne
    private Show show;
    private Date bookedAt;
    private int amount;
}
