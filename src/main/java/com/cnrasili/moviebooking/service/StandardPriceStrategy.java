package com.cnrasili.moviebooking.service;

/**
 * Implements the standard pricing strategy.
 * <p>
 * This strategy applies <strong>no discount</strong>. The user pays the full base price.
 * It is typically used for regular adult customers who do not qualify for specific promotions.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class StandardPriceStrategy implements PriceStrategy {

    /**
     * Returns the base price as is, with no modifications.
     *
     * @param basePrice The original price of the ticket.
     * @return The same base price (100% of the original).
     */
    @Override
    public double calculateDiscount(double basePrice) {
        return basePrice;
    }
}
