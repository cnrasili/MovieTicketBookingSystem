package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.AgeLimitException;
import com.cnrasili.moviebooking.exception.PaymentFailedException;
import com.cnrasili.moviebooking.exception.SeatOccupiedException;
import com.cnrasili.moviebooking.model.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Core service class responsible for handling the ticket booking workflow.
 * <p>
 * This class orchestrates the interaction between user input, validation logic, and data storage.
 * It ensures that a ticket is only created if all business rules are met.
 * <br>
 * Key Responsibilities:
 * <ul>
 * <li>Validating seat availability via the {@link com.cnrasili.moviebooking.model.Bookable} interface.</li>
 * <li>Enforcing age restrictions based on movie ratings.</li>
 * <li>Calculating complex pricing (Base Price + Multipliers - Discounts).</li>
 * <li>Processing payments via {@link PaymentService}.</li>
 * <li>Persisting the successful booking to {@link CinemaSystem#soldTickets}.</li>
 * </ul>
 * </p>
 *
 * @author cnrasili
 * @version 2.0
 */
public class Booking {

    /**
     * Creates a new ticket for a customer after performing all necessary checks and financial transactions.
     * <p>
     * <strong>Pricing & Booking Logic:</strong>
     * <ol>
     * <li>Check if the seat is available.</li>
     * <li>Validate customer age against the movie's rating.</li>
     * <li>Calculate Base Price: (Movie Price * Hall Multiplier * Seat Multiplier).</li>
     * <li>Apply <b>First Session Discount</b> (10%) if applicable.</li>
     * <li>Apply <b>Strategy Discount</b> (e.g., Student Discount) on top of the base price.</li>
     * <li>Process payment for the final calculated amount.</li>
     * <li>Reserve the seat and register the ticket in the system.</li>
     * </ol>
     * </p>
     *
     * @param customer       The customer requesting the booking.
     * @param showTime       The selected showtime session.
     * @param seat           The specific seat selected by the customer.
     * @param priceStrategy  The pricing strategy to apply (e.g., StudentStrategy).
     * @param paymentService The service used to process the payment.
     * @param cardInfo       The credit card information provided by the user.
     * @return A valid, registered {@link Ticket} object.
     * @throws SeatOccupiedException  If the selected seat is already reserved or occupied.
     * @throws AgeLimitException      If the customer does not meet the age requirements.
     * @throws PaymentFailedException If the payment is rejected due to format, balance, or validity.
     */
    public Ticket createTicket(Customer customer, ShowTime showTime, Seat seat, PriceStrategy priceStrategy, PaymentService paymentService, String cardInfo)
            throws SeatOccupiedException, AgeLimitException, PaymentFailedException {

        if (!seat.isAvailable()) {
            throw new SeatOccupiedException("Seat " + seat.toString() + " is already occupied.");
        }

        validateAge(customer, showTime.getMovie());

        double basePrice = showTime.getMovie().calculatePrice() * seat.getPriceMultiplier() * showTime.getHall().getPriceMultiplier();

        double totalDiscountAmount = 0.0;

        if (isFirstSession(showTime)) {
            System.out.println(">> Automatic Discount: First Session Discount Applied (-10%)");

            PriceStrategy firstSessionStrategy = new FirstSessionStrategy();

            double discountedPrice = firstSessionStrategy.calculateDiscount(basePrice);

            totalDiscountAmount += (basePrice - discountedPrice);
        }

        double priceAfterStrategy = priceStrategy.calculateDiscount(basePrice);
        double strategyDiscountAmount = basePrice - priceAfterStrategy;

        totalDiscountAmount += strategyDiscountAmount;

        double finalPrice = basePrice - totalDiscountAmount;

        paymentService.processPayment(finalPrice, cardInfo);

        seat.reserve();
        String pnr = generatePNR();
        Ticket ticket = new Ticket(pnr, customer, showTime, seat, basePrice, finalPrice);
        CinemaSystem.soldTickets.add(ticket);
        return ticket;

    }

    /**
     * Determines if the given showtime is the first session of the day for that specific movie.
     * <p>
     * It queries {@link CinemaSystem#activeShowTimes} to check if there are any earlier
     * sessions for the same movie on the same date.
     * </p>
     *
     * @param currentShow The showtime to check.
     * @return {@code true} if no earlier showtime exists; {@code false} otherwise.
     */
    public boolean isFirstSession(ShowTime currentShow) {
        LocalDateTime current = currentShow.getTime();
        Movie movie = currentShow.getMovie();

        for (ShowTime s : CinemaSystem.activeShowTimes) {
            if (s.getMovie().equals(movie) && s.getTime().toLocalDate().equals(current.toLocalDate())) {
                if (s.getTime().isBefore(current)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validates if the customer's age matches the movie's age rating.
     *
     * @param customer The customer whose age is being checked.
     * @param movie    The movie containing the age restriction.
     * @throws AgeLimitException If the customer is younger than the required age (18, 13, or 7).
     */
    private void validateAge(Customer customer, Movie movie) throws AgeLimitException {
        int currentYear = LocalDateTime.now().getYear();
        int age = currentYear - customer.getBirthYear();
        AgeRating rating = movie.getAgeRating();

        if (rating == AgeRating.PLUS_18 && age < 18) throw new AgeLimitException("Customer age (" + age + ") is strictly below 18.");
        if (rating == AgeRating.PLUS_13 && age < 13) throw new AgeLimitException("Customer age (" + age + ") is strictly below 13.");
        if (rating == AgeRating.PLUS_7 && age < 7) throw new AgeLimitException("Customer age (" + age + ") is strictly below 7.");
    }

    /**
     * Generates a unique 8-character Passenger Name Record (PNR) code.
     *
     * @return A random alphanumeric string (e.g., "A1B2C3D4").
     */
    private String generatePNR() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
