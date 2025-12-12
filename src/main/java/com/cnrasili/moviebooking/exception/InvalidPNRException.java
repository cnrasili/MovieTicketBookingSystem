package com.cnrasili.moviebooking.exception;

/**
 * Thrown when an operation is attempted using a PNR code that does not exist in the system.
 * <p>
 * This typically occurs during ticket cancellation or refund processes when the user
 * enters a PNR code that matches no sold ticket.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class InvalidPNRException extends Exception {

    /**
     * Constructs a new InvalidPNRException with the specified detail message.
     *
     * @param message The detail message explaining why the PNR is invalid.
     */
    public InvalidPNRException(String message) {
        super(message);
    }
}
