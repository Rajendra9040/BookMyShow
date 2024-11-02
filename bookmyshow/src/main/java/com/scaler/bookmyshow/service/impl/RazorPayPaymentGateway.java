package com.scaler.bookmyshow.service.impl;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.scaler.bookmyshow.service.PaymentGateway;
import com.scaler.bookmyshow.service.annotation.BmsService;
import com.scaler.bookmyshow.utils.BMSConstant;
import org.json.JSONObject;


@BmsService(BMSConstant.RAZOR_PAY)
public class RazorPayPaymentGateway implements PaymentGateway {
    @Override
    public String generatePaymentLink(String bookingId, Long amount) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount*100);
        paymentLinkRequest.put("currency",BMSConstant.INR);
        paymentLinkRequest.put("accept_partial",true);
        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",1691097057);
        paymentLinkRequest.put("reference_id","TS1989");
        paymentLinkRequest.put("description","Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put("name","+919000090000");
        customer.put("contact","Gaurav Kumar");
        customer.put("email","gaurav.kumar@example.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name","Jeevan Bima");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method","get");

//        dummy api call
//        PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

        return "https://razorpay.com/dummy-link";
    }

    @Override
    public String madePayment(String bookingId, Long amount) {
        return null;
    }
}
