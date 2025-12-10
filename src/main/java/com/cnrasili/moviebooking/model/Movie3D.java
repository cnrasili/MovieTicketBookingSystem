package com.cnrasili.moviebooking.model;

/**
 * Represents a 3D movie experience.
 * <p>
 * This format applies a fixed surcharge to the base price to cover
 * the cost of 3D technology (glasses, projection, etc.).
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class Movie3D extends Movie {

    /** The fixed additional cost added to the base price for 3D movies (50.0 TL). */
    private static final double THREE_D_SURCHARGE = 50.0;

    /**
     * Constructs a new 3D Movie.
     *
     * @param title           The title of the movie.
     * @param durationMinutes The duration in minutes.
     * @param basePrice       The base price of the ticket.
     * @param genre           The genre of the movie.
     * @param ageRating       The age restriction rating.
     */
    public Movie3D(String title, int durationMinutes, double basePrice, Genre genre, AgeRating ageRating) {
        super(title, durationMinutes, basePrice, genre, ageRating);
    }

    /**
     * Calculates the price for a 3D movie.
     * Adds the fixed surcharge {@value #THREE_D_SURCHARGE} to the base price.
     *
     * @return The total price (Base Price + Surcharge).
     */
    @Override
    public double calculatePrice() {
        return getBasePrice() + THREE_D_SURCHARGE;
    }
}
