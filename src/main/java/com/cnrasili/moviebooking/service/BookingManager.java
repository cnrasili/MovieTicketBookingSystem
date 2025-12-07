package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.AgeLimitException;
import com.cnrasili.moviebooking.exception.InvalidPNRException;
import com.cnrasili.moviebooking.exception.PaymentFailedException;
import com.cnrasili.moviebooking.exception.SeatOccupiedException;
import com.cnrasili.moviebooking.model.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingManager {

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
