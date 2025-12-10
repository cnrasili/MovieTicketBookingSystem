package com.cnrasili.moviebooking.model;

/**
 * Abstract base class representing a generic seat within a cinema hall.
 * <p>
 * This class serves as the foundation for different seat types (e.g., {@link StandardSeat}, {@link LoveSeat}).
 * It manages the seat's location (row and number) and its current availability status ({@link SeatStatus}).
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public abstract class Seat {
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
     * Reserves the seat by changing its status to {@link SeatStatus#BOOKED}.
     */
    public void reserve() {
        this.status = SeatStatus.BOOKED;
    }

    /**
     * Cancels the reservation by resetting the status to {@link SeatStatus#AVAILABLE}.
     * This is typically used during refund operations.
     */
    public void cancelBooking() {
        this.status = SeatStatus.AVAILABLE;
    }

    /**
     * Checks if the seat is currently available for booking.
     *
     * @return {@code true} if the status is AVAILABLE; {@code false} otherwise.
     */
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
