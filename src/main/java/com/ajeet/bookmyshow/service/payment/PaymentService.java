package com.ajeet.bookmyshow.service.payment;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @CircuitBreaker(name = "payment", fallbackMethod = "fallbackCharge")
    public boolean charge(String paymentToken, double amount) {
        //Payment service integration
        return true;
    }

    public boolean fallbackCharge(String paymentToken, double amount, Throwable t) {
        return false;
    }
}
