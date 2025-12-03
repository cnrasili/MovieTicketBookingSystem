package com.cnrasili.moviebooking.service;

public class StandardPriceStrategy implements PriceStrategy {
    @Override
    public double calculateDiscount(double basePrice) {
        return basePrice;
    }
}
