package com.cnrasili.moviebooking.model;

/**
 * Represents the current availability status of a seat in a cinema hall.
 *
 * @author cnrasili
 * @version 1.0
 */
public enum SeatStatus {
    /** The seat is free and can be booked. */
    AVAILABLE,

    /** The seat has been sold and is occupied. */
    BOOKED,

    /** The seat can't be booked. */
    BLOCKED
}
