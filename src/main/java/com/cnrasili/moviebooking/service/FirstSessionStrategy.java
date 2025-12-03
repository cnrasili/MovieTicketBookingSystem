package com.cnrasili.moviebooking.service;

public class FirstSessionStrategy implements PriceStrategy {
    @Override
    public double calculateDiscount(double basePrice) {
        return basePrice * 0.90;
    }
}
