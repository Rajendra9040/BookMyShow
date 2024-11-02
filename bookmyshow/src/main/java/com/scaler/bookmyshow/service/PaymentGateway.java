package com.scaler.bookmyshow.service;

import com.razorpay.RazorpayException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface PaymentGateway {
    String generatePaymentLink(String bookingId, Long amount) throws JSONException, RazorpayException;
    String madePayment(String bookingId, Long amount) ;
}
