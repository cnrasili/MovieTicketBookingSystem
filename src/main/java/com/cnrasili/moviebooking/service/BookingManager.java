package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.AgeLimitException;
import com.cnrasili.moviebooking.exception.InvalidPNRException;
import com.cnrasili.moviebooking.exception.PaymentFailedException;
import com.cnrasili.moviebooking.exception.SeatOccupiedException;
import com.cnrasili.moviebooking.model.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Manages the core business logic for the movie ticket booking system.
 * <p>
 * This class orchestrates the booking flow by:
 * <ul>
 * <li>Validating seat availability and customer age eligibility.</li>
 * <li>Calculating the final price with dynamic discounts (First Session, Strategy, etc.).</li>
 * <li>Processing payments via the {@link PaymentService}.</li>
 * <li>Generating tickets with unique PNR codes.</li>
 * </ul>
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class BookingManager {

    /**
     * Creates a new ticket for a customer after performing all necessary checks and financial transactions.
     * <p>
     * <strong>Pricing Logic:</strong>
     * <ol>
     * <li>Base Price Calculation: (Movie Price * Hall Multiplier * Seat Multiplier).</li>
     * <li>First Session Discount: -10% if applicable.</li>
     * <li>Strategy Discount: Deducted based on user type (e.g., Student).</li>
     * </ol>
     * </p>
     *
     * @param customer       The customer requesting the booking.
     * @param showTime       The selected showtime session.
     * @param seat           The specific seat selected by the customer.
     * @param priceStrategy  The pricing strategy to apply (e.g., StudentStrategy).
     * @param paymentService The service used to process the payment.
     * @param cardInfo       The credit card information provided by the user.
     * @return A valid {@link Ticket} object if the process is successful.
     * @throws SeatOccupiedException  If the seat is already booked.
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
            totalDiscountAmount += basePrice * 0.10;
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
     *
     * @param currentShow The showtime to check.
     * @return {@code true} if no earlier showtime exists for the same movie on the same day.
     */
    private boolean isFirstSession(ShowTime currentShow) {
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

    public boolean cancelTicket(String pnrCode) throws InvalidPNRException {
        Ticket ticket = CinemaSystem.searchTicketByPNR(pnrCode);
        if (ticket == null) {
            throw new InvalidPNRException("No ticket found with PNR: " + pnrCode);
        }
        CinemaSystem.soldTickets.remove(ticket);
        return true;
    }

    /**
     * Validates if the customer's age matches the movie's age rating.
     *
     * @param customer The customer.
     * @param movie    The movie.
     * @throws AgeLimitException If the customer is too young for the movie.
     */
    private void validateAge(Customer customer, Movie movie) throws AgeLimitException {
        int currentYear = LocalDateTime.now().getYear();
        int age = currentYear - customer.getBirthYear();
        AgeRating rating = movie.getAgeRating();

        if (rating == AgeRating.PLUS_18 && age < 18) throw new AgeLimitException("Customer age (" + age + ") is strictly below 18.");
        if (rating == AgeRating.PLUS_13 && age < 13) throw new AgeLimitException("Customer age (" + age + ") is strictly below 13.");
        if (rating == AgeRating.PLUS_7 && age < 7) throw new AgeLimitException("Customer age (" + age + ") is strictly below 7.");
    }

    private String generatePNR() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
