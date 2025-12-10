package com.cnrasili.moviebooking.model;

/**
 * Represents an IMAX cinema hall known for its large screen and immersive experience.
 * <p>
 * This hall type applies a price multiplier of 1.5.
 * Like other special halls, it features {@link LoveSeat}s in the last row.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class IMAXHall extends CinemaHall {

    /**
     * Constructs an IMAXHall.
     *
     * @param name      The name of the hall.
     * @param totalRows Total rows.
     * @param totalCols Total columns.
     */
    public IMAXHall(String name, int totalRows, int totalCols) {
        super(name, totalRows, totalCols);
    }

    /**
     * Returns the price multiplier for an IMAX hall.
     *
     * @return 1.5 (50% extra charge).
     */
    @Override
    public double getPriceMultiplier() {
        return 1.5;
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
