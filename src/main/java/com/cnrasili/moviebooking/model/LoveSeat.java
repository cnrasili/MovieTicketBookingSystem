package com.cnrasili.moviebooking.model;

/**
 * Represents a premium double seat (LoveSeat), typically located in the back rows or VIP halls.
 * <p>
 * LoveSeats are designed for couples and occupy more space.
 * Therefore, they have a price multiplier of 2.0 (Double price).
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class LoveSeat extends Seat {

    /**
     * Constructs a LoveSeat at the specified location.
     *
     * @param row    The row number.
     * @param number The seat number.
     */
    public LoveSeat(int row, int number) {
        super(row, number);
    }

    /**
     * Returns the price multiplier for a LoveSeat.
     *
     * @return 2.0 (Double the standard price).
     */
    @Override
    public double getPriceMultiplier() {
        return 2.0;
    }

    /**
     * Returns the visual representation of the LoveSeat for the console map.
     * <p>
     * Format:
     * <ul>
     * <li>Available: [L{row}-{number}] (e.g., [L5-1]) - 'L' indicates LoveSeat.</li>
     * <li>Booked: [ X X ]</li>
     * </ul>
     * </p>
     *
     * @return The formatted string.
     */
    @Override
    public String toString() {
        return isAvailable() ? "[L" + getRow() + "-" + getNumber() + "]" : "[ X X ]";
    }
}
