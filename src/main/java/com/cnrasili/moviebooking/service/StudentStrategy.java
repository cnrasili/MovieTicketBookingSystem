package com.cnrasili.moviebooking.service;

public class StudentStrategy implements PriceStrategy {
    @Override
    public double calculateDiscount(double basePrice) {
        System.out.println(">> Student Discount Applied (-20%)");
        return basePrice * 0.80;
    }
}
