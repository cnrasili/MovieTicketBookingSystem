package com.cnrasili.moviebooking.service;
import com.cnrasili.moviebooking.exception.PaymentFailedException;

public interface PaymentService {
    void processPayment(double amount, String cardInfo) throws PaymentFailedException;
}
