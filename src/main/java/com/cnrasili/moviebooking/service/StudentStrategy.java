package com.cnrasili.moviebooking.service;

/**
 * Implements the student pricing strategy.
 * <p>
 * This strategy applies a <strong>20% discount</strong> to the base price for verified students.
 * It also logs a confirmation message to the console when applied.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class StudentStrategy implements PriceStrategy {

    /**
     * Calculates the discounted price for students.
     * <p>
     * Formula: {@code basePrice * 0.80}
     * </p>
     *
     * @param basePrice The original price of the ticket.
     * @return The discounted price (80% of the original).
     */
    @Override
    public double calculateDiscount(double basePrice) {
        System.out.println(">> Student Discount Applied (-20%)");
        return basePrice * 0.80;
    }
}
