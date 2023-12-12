package com.scaler.bookmyshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseModel{
    private String referenceNumber;
    private int amount;
    @Enumerated(EnumType.STRING)
    private PaymentProvider paymentProvider;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
