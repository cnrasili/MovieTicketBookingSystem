package com.cnrasili.moviebooking.service;

public class StudentStrategy implements PriceStrategy {
    @Override
    public double calculateDiscount(double basePrice) {
        return basePrice * 0.80;
    }
}
