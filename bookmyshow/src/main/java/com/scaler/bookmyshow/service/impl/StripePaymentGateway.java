package com.scaler.bookmyshow.service.impl;

import com.razorpay.RazorpayException;
import com.scaler.bookmyshow.service.PaymentGateway;
import com.scaler.bookmyshow.service.annotation.BmsService;
import com.scaler.bookmyshow.utils.BMSConstant;
import org.springframework.boot.configurationprocessor.json.JSONException;


@BmsService(BMSConstant.STRIPE)
public class StripePaymentGateway implements PaymentGateway {
    @Override
    public String generatePaymentLink(String bookingId, Long amount) {
        // stripe dummy implementation
        return "https://dashboard.stripe.com/dummy-link";
    }

    @Override
    public String madePayment(String bookingId, Long amount) {
        return null;
    }
}
