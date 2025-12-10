package com.cnrasili.moviebooking.model;

/**
 * Represents a standard 2D movie.
 * <p>
 * This format does not incur any additional charges. The ticket price is
 * equal to the base price defined in the {@link Movie} class.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class Movie2D extends Movie {

    /**
     * Constructs a new 2D Movie.
     *
     * @param title           The title of the movie.
     * @param durationMinutes The duration in minutes.
     * @param basePrice       The base price of the ticket.
     * @param genre           The genre of the movie.
     * @param ageRating       The age restriction rating.
     */
    public Movie2D(String title, int durationMinutes, double basePrice, Genre genre, AgeRating ageRating) {
        super(title, durationMinutes, basePrice, genre, ageRating);
    }

    /**
     * Calculates the price for a 2D movie.
     * Since there are no extra costs for 2D, it returns the base price directly.
     *
     * @return The base price of the movie.
     */
    @Override
    public double calculatePrice() {
        return getBasePrice();
    }
}
