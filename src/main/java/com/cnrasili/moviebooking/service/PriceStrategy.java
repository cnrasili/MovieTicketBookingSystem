package com.cnrasili.moviebooking.service;

/**
 * Defines the contract for dynamic pricing strategies (Strategy Design Pattern).
 * <p>
 * Classes implementing this interface define specific algorithms for calculating
 * ticket prices based on different rules (e.g., Student discount, Standard price).
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public interface PriceStrategy {

    /**
     * Calculates the final price to be paid after applying the specific strategy's logic.
     *
     * @param basePrice The original base price of the ticket before strategy application.
     * @return The calculated price (e.g., if base is 100 and strategy is 20% off, returns 80).
     */
    double calculateDiscount(double basePrice);
}
