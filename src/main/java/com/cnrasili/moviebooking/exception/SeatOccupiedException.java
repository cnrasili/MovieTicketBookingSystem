package com.cnrasili.moviebooking.exception;

/**
 * Thrown when attempting to book a seat that has already been reserved.
 * <p>
 * This prevents double booking conflicts by ensuring a seat cannot be sold
 * to more than one customer for the same showtime.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class SeatOccupiedException extends Exception {

    /**
     * Constructs a new SeatOccupiedException with the specified detail message.
     *
     * @param message The detail message identifying the occupied seat.
     */
    public SeatOccupiedException(String message) {
        super(message);
    }
}
