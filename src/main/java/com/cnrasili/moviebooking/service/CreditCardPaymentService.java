package com.cnrasili.moviebooking.service;

public class CreditCardPaymentService implements PaymentService {
    @Override
    public boolean processPayment(double amount, String cardInfo) {
        if (cardInfo != null && cardInfo.matches("^[0-9]{16}$")) {
            System.out.println(">> Payment of " + amount + " TL processed successfully. (Card ending with " + cardInfo.substring(12) + ")");
            return true;
        } else {
            System.out.println(">> Payment Failed: Invalid Card Transaction.");
            return false;
        }
    }
}
