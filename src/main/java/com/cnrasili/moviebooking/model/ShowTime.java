package com.cnrasili.moviebooking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific movie screening session (Seans).
 * <p>
 * A ShowTime links a {@link Movie} to a {@link CinemaHall} at a specific time.
 * Crucially, it manages its own independent copy of the seat map to ensure bookings
 * in one session do not affect others.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class ShowTime {
    private LocalDateTime time;
    private Movie movie;
    private CinemaHall hall;
    private List<Seat> seats;

    /**
     * Constructs a new ShowTime session.
     *
     * @param time  The date and time of the screening.
     * @param movie The movie being shown.
     * @param hall  The hall where the screening takes place.
     */
    public ShowTime(LocalDateTime time, Movie movie, CinemaHall hall) {
        this.time = time;
        this.movie = movie;
        this.hall = hall;
        this.seats = new ArrayList<>();
        initSeats();
    }

    /**
     * Creates a deep copy of the seating arrangement from the CinemaHall.
     * <p>
     * This method iterates through the hall's "blueprint" seats and creates new
     * instances (either {@link LoveSeat} or {@link StandardSeat}) for this specific session.
     * This ensures that {@code seat.reserve()} affects only this ShowTime.
     * </p>
     */
    private void initSeats() {
        for (Seat originalSeat : hall.getSeats()) {

            if (originalSeat instanceof LoveSeat) {
                this.seats.add(new LoveSeat(originalSeat.getRow(), originalSeat.getNumber()));
            }
            else {
                this.seats.add(new StandardSeat(originalSeat.getRow(), originalSeat.getNumber()));
            }
        }
    }

    /**
     * Retrieves a specific seat within this session based on row and number.
     *
     * @param row    The row number.
     * @param number The seat number.
     * @return The {@link Seat} object if found, otherwise {@code null}.
     */
    public Seat getSeat(int row, int number) {
        for (Seat seat : seats) {
            if (seat.getRow() == row && seat.getNumber() == number) {
                return seat;
            }
        }
        return null;
    }

    /**
     * Calculates the standard ticket price for this session.
     * Formula: Movie Base Price * Hall Price Multiplier.
     *
     * @return The standard price (before any user-specific discounts).
     */
    public double getStandardPrice() {
        return movie.calculatePrice() * hall.getPriceMultiplier();
    }

    public Movie getMovie() { return movie; }
    public CinemaHall getHall() { return hall; }
    public List<Seat> getSeats() { return seats; }
    public LocalDateTime getTime() { return time; }

    /**
     * Returns a formatted string describing the session.
     * Format: "dd-MM-yyyy HH:mm | HallName (Price: X TL)"
     *
     * @return Formatted session details.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return time.format(formatter) + " | " + hall.getName() + " (Price: " + getStandardPrice() + " TL)";
    }
}
