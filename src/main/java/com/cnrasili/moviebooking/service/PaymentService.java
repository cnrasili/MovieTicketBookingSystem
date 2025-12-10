package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.PaymentFailedException;

/**
 * Defines the contract for payment processing services within the booking system.
 * <p>
 * Implementations of this interface handle the communication with payment gateways
 * or mock banking systems to process financial transactions.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public interface PaymentService {

    /**
     * Processes a payment transaction for a specific amount.
     * <p>
     * If the transaction is successful, the method completes normally.
     * If the transaction fails (e.g., insufficient funds, invalid card), an exception is thrown.
     * </p>
     *
     * @param amount   The total monetary amount to be deducted.
     * @param cardInfo The card credentials (e.g., 16-digit card number) required for the transaction.
     * @throws PaymentFailedException If the payment is rejected by the system.
     */
    void processPayment(double amount, String cardInfo) throws PaymentFailedException;
}
