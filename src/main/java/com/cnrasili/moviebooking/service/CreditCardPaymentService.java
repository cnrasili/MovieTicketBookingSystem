package com.cnrasili.moviebooking.service;
import com.cnrasili.moviebooking.exception.PaymentFailedException;
import java.util.HashMap;
import java.util.Map;

public class CreditCardPaymentService implements PaymentService {

    private final Map<String, Double> cardDatabase;

    public CreditCardPaymentService() {
        cardDatabase = new HashMap<>();

        cardDatabase.put("1111111111111111", 5000.0);
        cardDatabase.put("2222222222222222", 200.0);
        cardDatabase.put("3333333333333333", 50.0);
    }

    @Override
    public void processPayment(double amount, String cardInfo) throws PaymentFailedException {

        if (cardInfo == null || !cardInfo.matches("^[0-9]{16}$")) {
            throw new PaymentFailedException("Invalid Card Number Format (Must be 16 digits).");
        }

        if (!cardDatabase.containsKey(cardInfo)) {
            throw new PaymentFailedException("Card not found in bank database.");
        }

        double currentBalance = cardDatabase.get(cardInfo);

        if (currentBalance < amount) {
            throw new PaymentFailedException("Insufficient Funds! (Balance: " + currentBalance + " TL, Required: " + amount + " TL)");
        }

        double newBalance = currentBalance - amount;
        cardDatabase.put(cardInfo, newBalance);

        System.out.println(">> Payment Approved! " + amount + " TL deducted.");
        System.out.println(">> Remaining Balance: " + newBalance + " TL");
    }
}
