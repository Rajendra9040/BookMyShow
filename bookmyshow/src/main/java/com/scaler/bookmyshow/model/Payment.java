package com.scaler.bookmyshow.model;

import com.scaler.bookmyshow.model.enums.PaymentProvider;
import com.scaler.bookmyshow.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseModel{
    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "amount")
    private int amount;

    @Column(name = "payment_provider_id")
    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;

    @Column(name = "payment_status_id")
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
