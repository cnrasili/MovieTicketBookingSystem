package com.cnrasili.moviebooking.service;
import com.cnrasili.moviebooking.exception.PaymentFailedException;

/**
 * A simulation implementation of {@link PaymentService} using an in-memory mock database.
 * <p>
 * This class simulates a bank system where specific credit cards have predefined balances.
 * It validates card formats and checks for sufficient funds before approval.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class CreditCardPaymentService implements PaymentService {

    /**
     * Constructs the service and initializes the mock database with test data.
     * <p>
     * Predefined Cards:
     * <ul>
     * <li>1111... -> 5000.0 TL (Rich)</li>
     * <li>2222... -> 200.0 TL (Standard)</li>
     * <li>3333... -> 50.0 TL (Poor)</li>
     * </ul>
     * </p>
     */
    public CreditCardPaymentService() {

    }

    /**
     * Processes a credit card payment.
     * <p>
     * Validation Steps:
     * <ol>
     * <li>Format Check: Must be exactly 16 digits.</li>
     * <li>Existence Check: Must exist in the mock database.</li>
     * <li>Balance Check: Must have enough balance to cover the amount.</li>
     * </ol>
     * </p>
     *
     * @param amount   The amount to withdraw.
     * @param cardInfo The 16-digit card number.
     * @throws PaymentFailedException If validation fails or funds are insufficient.
     */
    @Override
    public void processPayment(double amount, String cardInfo) throws PaymentFailedException {

        if (cardInfo == null || !cardInfo.matches("^[0-9]{16}$")) {
            throw new PaymentFailedException("Invalid Card Number Format (Must be 16 digits).");
        }

        if (!CinemaSystem.mockCardDB.containsKey(cardInfo)) {
            throw new PaymentFailedException("Card not found in bank database.");
        }

        double currentBalance = CinemaSystem.mockCardDB.get(cardInfo);

        if (currentBalance < amount) {
            throw new PaymentFailedException("Insufficient Funds! (Balance: " + currentBalance + " TL, Required: " + amount + " TL)");
        }

        double newBalance = currentBalance - amount;
        CinemaSystem.mockCardDB.put(cardInfo, newBalance);

        System.out.println(">> Payment Approved! " + amount + " TL deducted.");
        System.out.println(">> Remaining Balance: " + newBalance + " TL");
    }
}
