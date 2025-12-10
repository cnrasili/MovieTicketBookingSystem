package com.cnrasili.moviebooking.model;

/**
 * Abstract base class representing a generic movie in the system.
 * <p>
 * This class holds the fundamental properties of a movie such as title, duration,
 * genre, and age rating. It serves as the parent for specific movie formats
 * (e.g., {@link Movie2D}, {@link Movie3D}).
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public abstract class Movie {
    private String title;
    private int durationMinutes;
    private double basePrice;
    private Genre genre;
    private AgeRating ageRating;

    /**
     * Constructs a new Movie with the specified details.
     *
     * @param title           The title of the movie.
     * @param durationMinutes The total duration of the movie in minutes.
     * @param basePrice       The starting price of the ticket before format-specific surcharges.
     * @param genre           The category of the movie (e.g., ACTION, DRAMA).
     * @param ageRating       The age restriction level (e.g., PLUS_18).
     */
    public Movie(String title, int durationMinutes, double basePrice, Genre genre, AgeRating ageRating) {
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.basePrice = basePrice;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    /**
     * Calculates the price of the movie based on its format (2D/3D).
     * <p>
     * Subclasses must implement this method to apply specific pricing rules,
     * such as adding surcharges for 3D glasses or technology.
     * </p>
     *
     * @return The calculated price for the movie format.
     */
    public abstract double calculatePrice();

    public String getTitle() { return title; }
    public int getDurationMinutes() { return durationMinutes; }
    public double getBasePrice() { return basePrice; }
    public Genre getGenre() { return genre; }
    public AgeRating getAgeRating() { return ageRating; }

    /**
     * Returns a string representation of the movie details.
     * Format: Title [Rating] (Genre) - Duration min
     *
     * @return A formatted string describing the movie.
     */
    @Override
    public String toString() {
        return title + " [" + ageRating + "] (" + genre + ") - " + durationMinutes + " min";
    }
}
