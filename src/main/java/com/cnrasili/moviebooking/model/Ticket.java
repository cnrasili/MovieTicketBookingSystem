package com.cnrasili.moviebooking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private String pnrCode;
    private LocalDateTime creationDate;
    private Customer owner;
    private ShowTime showTime;
    private Seat seat;
    private double originalPrice;
    private double finalPrice;

    public Ticket(String pnrCode, Customer owner, ShowTime showTime, Seat seat, double originalPrice, double finalPrice) {
        this.pnrCode = pnrCode;
        this.owner = owner;
        this.showTime = showTime;
        this.seat = seat;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.creationDate = LocalDateTime.now();
    }

    public void printTicketInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.println("=========================================");
        System.out.println("           MOVIE TICKET (PNR: " + pnrCode + ")");
        System.out.println("=========================================");
        System.out.println("Movie    : " + showTime.getMovie().getTitle());
        System.out.println("Rating   : " + showTime.getMovie().getAgeRating());
        System.out.println("Time     : " + showTime.getTime().format(formatter));
        System.out.println("Hall     : " + showTime.getHall().getName());

        String seatPrefix = (seat instanceof LoveSeat) ? "L" : "R"; // LoveSeat ise L, değilse R
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
