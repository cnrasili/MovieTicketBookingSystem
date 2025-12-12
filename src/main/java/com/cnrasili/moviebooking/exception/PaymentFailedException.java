package com.cnrasili.moviebooking.exception;

/**
 * Thrown when a financial transaction cannot be completed successfully.
 * <p>
 * Reasons for this exception include:
 * <ul>
 * <li>Insufficient funds in the provided card.</li>
 * <li>Invalid card number format.</li>
 * <li>Card not found in the bank database.</li>
 * </ul>
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class PaymentFailedException extends Exception {

    /**
     * Constructs a new PaymentFailedException with the specified detail message.
     *
     * @param message The detail message explaining the reason for payment failure.
     */
    public PaymentFailedException(String message) {
        super(message);
    }
}
