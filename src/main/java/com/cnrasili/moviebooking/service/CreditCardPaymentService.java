package com.cnrasili.moviebooking.service;

public class CreditCardPaymentService implements PaymentService {
    @Override
    public boolean processPayment(double amount, String cardInfo) {
        return true;
    }
}
