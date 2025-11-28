package com.cnrasili.moviebooking.model;

public abstract class Movie {
    private String title;
    private int durationMinutes;
    private double basePrice;
    private Genre genre;
    private AgeRating ageRating;

    public Movie(String title, int durationMinutes, double basePrice, Genre genre, AgeRating ageRating) {
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.basePrice = basePrice;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    public abstract double calculatePrice();

    public String getTitle() { return title; }
    public int getDurationMinutes() { return durationMinutes; }
    public double getBasePrice() { return basePrice; }
    public Genre getGenre() { return genre; }
    public AgeRating getAgeRating() { return ageRating; }

    @Override
    public String toString() {
        return title + " (" + genre + ") - " + durationMinutes + " min";
    }
}
