package com.scaler.bookmyshow.service.factory;

import com.scaler.bookmyshow.service.PaymentGateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentGatewayFactory extends AbstractServiceFactory<PaymentGateway>{
    protected PaymentGatewayFactory(List<PaymentGateway> services) {
        super(services);
    }
}
