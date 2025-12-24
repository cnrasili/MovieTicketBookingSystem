package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.PaymentFailedException;

/**
 * Implementation of {@link PaymentService} that simulates credit card transactions.
 * <p>
 * Unlike a real banking gateway, this service validates transactions against a central mock database
 * located in {@link CinemaSystem#mockCardDB}.
 * <br>
 * It checks for:
 * <ul>
 * <li>Card existence (loaded from CSV).</li>
 * <li>Sufficient balance for the transaction.</li>
 * </ul>
 * </p>
 *
 * @author cnrasili
 * @version 1.2
 */
public class CreditCardPaymentService implements PaymentService {

    /**
     * Constructs the service instance.
     * <p>
     * Credit card data is loaded from {@code credit_cards.csv} into {@link CinemaSystem}
     * by the {@link DataInitializer} at application startup.
     * </p>
     */
    public CreditCardPaymentService() {

    }

    /**
     * Processes a credit card payment by validating against the mock database.
     * <p>
     * Validation Steps:
     * <ol>
     * <li><b>Format Check:</b> Must be exactly 16 digits.</li>
     * <li><b>Existence Check:</b> Queries {@link CinemaSystem#mockCardDB} to see if card exists.</li>
     * <li><b>Balance Check:</b> Ensures the card has enough funds.</li>
     * </ol>
     * If successful, the new balance is updated directly in {@link CinemaSystem}.
     * </p>
     *
     * @param amount   The amount to withdraw.
     * @param cardInfo The 16-digit card number.
     * @throws PaymentFailedException If validation fails, card is not found, or funds are insufficient.
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
