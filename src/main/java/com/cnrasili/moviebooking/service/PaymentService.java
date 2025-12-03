package com.cnrasili.moviebooking.service;

public interface PaymentService {
    boolean processPayment(double amount, String cardInfo);
}
