package com.cnrasili.moviebooking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a confirmed booking receipt (Ticket).
 * <p>
 * Stores a snapshot of the booking details including the PNR code,
 * final calculated price, seat information, and timestamp.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class Ticket {
    private String pnrCode;
    private LocalDateTime creationDate;
    private Customer owner;
    private ShowTime showTime;
    private Seat seat;
    private double originalPrice;
    private double finalPrice;

    /**
     * Constructs a new Ticket.
     *
     * @param pnrCode       Unique Passenger Name Record code.
     * @param owner         The customer who owns the ticket.
     * @param showTime      The session for which the ticket is issued.
     * @param seat          The booked seat.
     * @param originalPrice The price before discounts.
     * @param finalPrice    The actual amount paid.
     */
    public Ticket(String pnrCode, Customer owner, ShowTime showTime, Seat seat, double originalPrice, double finalPrice) {
        this.pnrCode = pnrCode;
        this.owner = owner;
        this.showTime = showTime;
        this.seat = seat;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Prints the formatted ticket details to the console.
     * <p>
     * Displays movie info, seat location (with 'L' prefix for LoveSeats),
     * customer details, and a price breakdown showing discounts if applicable.
     * </p>
     */
    public void printTicketInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.println("=========================================");
        System.out.println("           MOVIE TICKET (PNR: " + pnrCode + ")");
        System.out.println("=========================================");
        System.out.println("Movie    : " + showTime.getMovie().getTitle());
        System.out.println("Rating   : " + showTime.getMovie().getAgeRating());
        System.out.println("Time     : " + showTime.getTime().format(formatter));
        System.out.println("Hall     : " + showTime.getHall().getName());

        String seatPrefix = (seat instanceof LoveSeat) ? "L" : "R"; // L for LoveSeat, R for otherwise
        String seatInfo = seatPrefix + seat.getRow() + "-N" + seat.getNumber();
        System.out.println("Seat     : " + seatInfo);

        System.out.println("Customer : " + owner.getFullName());
        System.out.println("-----------------------------------------");

        if (originalPrice > finalPrice) {
            System.out.println("Original Price : " + originalPrice + " TL");
            System.out.println("Discount       : -" + (originalPrice - finalPrice) + " TL");
            System.out.println("TOTAL PRICE    :  " + finalPrice + " TL");
        } else {
            System.out.println("TOTAL PRICE    : " + finalPrice + " TL");
        }

        System.out.println("Date: " + creationDate.format(formatter));
        System.out.println("=========================================");
    }

    public String getPnrCode() { return pnrCode; }
    public ShowTime getShowTime() { return showTime; }
    public Seat getSeat() { return seat; }
    public double getFinalPrice() { return finalPrice; }
}
