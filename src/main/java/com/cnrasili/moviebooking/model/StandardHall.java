package com.cnrasili.moviebooking.model;

/**
 * Represents a standard cinema hall configuration.
 * <p>
 * This hall type has a base price multiplier of 1.0.
 * It is configured with {@link LoveSeat}s in the last row and {@link StandardSeat}s elsewhere.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class StandardHall extends CinemaHall {

    /**
     * Constructs a StandardHall.
     *
     * @param name      The name of the hall.
     * @param totalRows Total rows.
     * @param totalCols Total columns.
     */
    public StandardHall(String name, int totalRows, int totalCols) {
        super(name, totalRows, totalCols);
    }

    /**
     * Returns the price multiplier for a standard hall.
     *
     * @return 1.0 (No extra charge).
     */
    @Override
    public double getPriceMultiplier() {
        return 1.0;
    }

    /**
     * Initializes seats with a custom layout:
     * Last row contains {@link LoveSeat}, other rows contain {@link StandardSeat}.
     */
    @Override
    protected void initSeats() {
        getSeats().clear();

        for (int row = 1; row <= getTotalRows(); row++) {
            for (int col = 1; col <= getTotalCols(); col++) {
                if (row == getTotalRows()) {
                    getSeats().add(new LoveSeat(row, col));
                } else {
                    getSeats().add(new StandardSeat(row, col));
                }
            }
        }
    }
}
