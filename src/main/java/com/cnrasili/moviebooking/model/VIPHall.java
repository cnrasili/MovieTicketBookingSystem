package com.cnrasili.moviebooking.model;

/**
 * Represents a luxury VIP cinema hall.
 * <p>
 * This hall type applies a significant price multiplier (2.0) due to premium services.
 * It features {@link LoveSeat}s in the back row for added comfort.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class VIPHall extends CinemaHall {

    /**
     * Constructs a VIPHall.
     *
     * @param name      The name of the hall.
     * @param totalRows Total rows.
     * @param totalCols Total columns.
     */
    public VIPHall(String name, int totalRows, int totalCols) {
        super(name, totalRows, totalCols);
    }

    /**
     * Returns the price multiplier for a VIP hall.
     *
     * @return 2.0 (Double price).
     */
    @Override
    public double getPriceMultiplier() {
        return 2.0;
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
