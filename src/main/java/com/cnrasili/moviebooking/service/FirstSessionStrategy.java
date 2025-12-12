package com.cnrasili.moviebooking.service;

/**
 * Implements a promotional pricing strategy for the first session of the day.
 * <p>
 * This strategy encourages early attendance by applying a <strong>10% discount</strong>
 * to the ticket price.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class FirstSessionStrategy implements PriceStrategy {

    /**
     * Calculates the discounted price for the first session.
     * <p>
     * Formula: {@code basePrice * 0.90}
     * </p>
     *
     * @param basePrice The original price of the ticket.
     * @return The discounted price (90% of the original).
     */
    @Override
    public double calculateDiscount(double basePrice) {
        return basePrice * 0.90;
    }
}
