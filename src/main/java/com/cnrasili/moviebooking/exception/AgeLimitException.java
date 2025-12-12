package com.cnrasili.moviebooking.exception;

/**
 * Thrown when a customer attempts to book a ticket for a movie that requires a higher age rating.
 * <p>
 * For example, if a 10-year-old customer tries to book a "PLUS_18" movie,
 * this checked exception is thrown to block the transaction.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class AgeLimitException extends Exception {

    /**
     * Constructs a new AgeLimitException with the specified detail message.
     *
     * @param message The detail message explaining the age restriction violation.
     */
    public AgeLimitException(String message) {
        super(message);
    }
}
