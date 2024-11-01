package com.scaler.bookmyshow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scaler.bookmyshow.model.enums.BookingStatus;
import com.scaler.bookmyshow.model.userAuth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking extends BaseModel {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "booking_show_seats",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "show_seat_id")
    )
    @JsonIgnore
    private List<ShowSeat> showSeats;

    @Column(name = "booking_status_id")
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @Column(name = "booked_at")
    private Date bookedAt;

    @Column(name = "amount")
    private int amount;
}
