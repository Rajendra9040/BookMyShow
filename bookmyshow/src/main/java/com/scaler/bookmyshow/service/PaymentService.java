package com.scaler.bookmyshow.service;

import com.scaler.bookmyshow.service.factory.PaymentGatewayFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentGatewayFactory paymentGatewayFactory;
    public void madePayment(String paymentGatewayType) {
        paymentGatewayFactory.getService(paymentGatewayType).madePayment("dummy",1000L);
    }

}
