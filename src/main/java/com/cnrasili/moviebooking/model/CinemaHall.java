package com.cnrasili.moviebooking.model;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class representing a cinema hall within a branch.
 * <p>
 * This class defines the common properties of all halls (name, dimensions, seat list)
 * and mandates specific behaviors for subclasses, such as price multipliers and seat initialization.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public abstract class CinemaHall {
    private String name;
    private int totalRows;
    private int totalCols;
    private List<Seat> seats;

    /**
     * Constructs a new CinemaHall and initializes the seating arrangement.
     *
     * @param name      The display name of the hall (e.g., "Salon 1", "IMAX").
     * @param totalRows The total number of rows in the hall.
     * @param totalCols The number of seats per row (columns).
     */
    public CinemaHall(String name, int totalRows, int totalCols) {
        this.name = name;
        this.totalRows = totalRows;
        this.totalCols = totalCols;
        this.seats = new ArrayList<>();
        initSeats();
    }

    /**
     * Initializes the seats for the hall.
     * <p>
     * The default implementation fills the hall with {@link StandardSeat} objects.
     * Subclasses (e.g., IMAX, VIP) can override this method to create custom layouts
     * (e.g., adding {@link LoveSeat}s to the back rows).
     * </p>
     */
    protected void initSeats() {
        for (int row = 1; row <= totalRows; row++) {
            for (int col = 1; col <= totalCols; col++) {
                seats.add(new StandardSeat(row, col));
            }
        }
    }

    /**
     * Returns the price multiplier specific to this hall type.
     * <p>
     * Used in ticket price calculation logic.
     * </p>
     *
     * @return The multiplier value (e.g., 1.5 for IMAX).
     */
    public abstract double getPriceMultiplier();

    public String getName() {
        return name;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    /**
     * Retrieves the list of seats in this hall.
     *
     * @return A list of {@link Seat} objects.
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Returns a string representation of the hall, including its name and dimensions.
     * <p>
     * Format: "Name (RowsxCols)" example: "IMAX Hall (5x5)"
     * </p>
     *
     * @return A formatted string describing the hall.
     */
    @Override
    public String toString() {
        return name + " (" + totalRows + "x" + totalCols + ")";
    }
}
