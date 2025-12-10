package com.cnrasili.moviebooking.model;

/**
 * Represents a standard single seat in a cinema hall.
 * <p>
 * This is the default seat type found in most halls.
 * It has a standard price multiplier of 1.0.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class StandardSeat extends Seat {

    /**
     * Constructs a StandardSeat at the specified location.
     *
     * @param row    The row number.
     * @param number The seat number.
     */
    public StandardSeat(int row, int number) {
        super(row, number);
    }

    /**
     * Returns the price multiplier for a standard seat.
     *
     * @return 1.0 (No extra charge).
     */
    @Override
    public double getPriceMultiplier() {
        return 1.0;
    }

    /**
     * Returns the visual representation of the seat for the console map.
     * <p>
     * Format:
     * <ul>
     * <li>Available: [R{row}-{number}] (e.g., [R1-1])</li>
     * <li>Booked: [ X X ]</li>
     * </ul>
     * </p>
     *
     * @return The formatted string.
     */
    @Override
    public String toString() {
        return isAvailable() ? "[R" + getRow() + "-" + getNumber() + "]" : "[ X X ]";
    }
}
