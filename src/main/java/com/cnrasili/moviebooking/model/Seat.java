package com.cnrasili.moviebooking.model;

/**
 * Abstract base class representing a seat in a cinema hall.
 * <p>
 * This class serves as the structural foundation for different seating types (e.g., {@link StandardSeat}, {@link LoveSeat})
 * and implements the {@link Bookable} interface to handle reservation logic uniformly.
 * <br>
 * Responsibilities:
 * <ul>
 * <li>Maintaining spatial location (Row/Number).</li>
 * <li>Tracking availability status via {@link SeatStatus}.</li>
 * <li>Defining abstract pricing rules via {@code getPriceMultiplier()}.</li>
 * </ul>
 * </p>
 *
 * @author cnrasili
 * @version 1.1
 */
public abstract class Seat implements Bookable {
    private int row;
    private int number;
    private SeatStatus status;

    /**
     * Constructs a new Seat at the specified location.
     * Initially, the seat status is set to {@link SeatStatus#AVAILABLE}.
     *
     * @param row    The row number of the seat.
     * @param number The column/seat number within the row.
     */
    public Seat(int row, int number) {
        this.row = row;
        this.number = number;
        this.status = SeatStatus.AVAILABLE;
    }

    /**
     * Returns the price multiplier for this specific type of seat.
     * <p>
     * Subclasses must implement this method to define their specific pricing logic.
     * For example, a VIP seat might have a higher multiplier than a standard seat.
     * </p>
     *
     * @return The price multiplier value (e.g., 1.0 for standard, 2.0 for LoveSeat).
     */
    public abstract double getPriceMultiplier();

    /**
     * Marks the seat as reserved.
     * <p>
     * Implements {@link Bookable#reserve()} by transitioning the internal status to {@link SeatStatus#BOOKED}.
     * </p>
     */
    @Override
    public void reserve() {
        this.status = SeatStatus.BOOKED;
    }

    /**
     * Cancels the reservation by resetting the status to {@link SeatStatus#AVAILABLE}.
     * <p>
     * Implements {@link Bookable#cancelBooking()}. This is typically used during refund operations.
     * </p>
     */
    @Override
    public void cancelBooking() {
        this.status = SeatStatus.AVAILABLE;
    }

    /**
     * Checks if the seat is currently available for booking.
     *
     * @return {@code true} if the status is {@link SeatStatus#AVAILABLE}; {@code false} otherwise.
     */
    @Override
    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public SeatStatus getStatus() {
        return status;
    }

    /**
     * Returns a string representation of the seat's location.
     *
     * @return A string like "R1-N1".
     */
    @Override
    public String toString() {
        return "R" + row + "-N" + number;
    }
}
