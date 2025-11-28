package com.cnrasili.moviebooking.model;

public class Movie2D extends Movie {

    public Movie2D(String title, int durationMinutes, double basePrice, Genre genre, AgeRating ageRating) {
        super(title, durationMinutes, basePrice, genre, ageRating);
    }

    @Override
    public double calculatePrice() {
        return getBasePrice();
    }
}
