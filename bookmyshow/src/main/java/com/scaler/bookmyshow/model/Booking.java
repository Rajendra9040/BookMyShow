package com.scaler.bookmyshow.model;

import com.scaler.bookmyshow.model.enums.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking extends BaseModel {
    @Column(name = "user_id")
    private Long userId;

    @ManyToMany
    private List<ShowSeat> showSeats;

    @Column(name = "booking_status_id")
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    @Column(name = "booked_at")
    private Date bookedAt;

    @Column(name = "amount")
    private int amount;

    @OneToMany
    private List<Payment> payments;
}
