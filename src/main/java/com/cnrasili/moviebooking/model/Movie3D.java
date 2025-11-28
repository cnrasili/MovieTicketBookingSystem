package com.cnrasili.moviebooking.model;

public class Movie3D extends Movie {
    private static final double THREE_D_SURCHARGE = 50.0;

    public Movie3D(String title, int durationMinutes, double basePrice, Genre genre, AgeRating ageRating) {
        super(title, durationMinutes, basePrice, genre, ageRating);
    }

    @Override
    public double calculatePrice() {
        return getBasePrice() + THREE_D_SURCHARGE;
    }
}
