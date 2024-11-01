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

    @Column(name = "payment_provider")
    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;

    @Column(name = "payment_status")
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    @Column(name = "booking_id")
    private Long bookingId;
}
