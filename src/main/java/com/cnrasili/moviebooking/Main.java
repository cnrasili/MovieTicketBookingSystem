package com.cnrasili.moviebooking;

import com.cnrasili.moviebooking.exception.AgeLimitException;
import com.cnrasili.moviebooking.exception.InvalidPNRException;
import com.cnrasili.moviebooking.exception.SeatOccupiedException;
import com.cnrasili.moviebooking.model.*;
import com.cnrasili.moviebooking.service.*;
import com.cnrasili.moviebooking.util.ConsoleHelper;

import java.util.List;

public class Main {
    private static final BookingManager bookingManager = new BookingManager();
    private static final RefundService refundService = new RefundService();
    private static final PaymentService paymentService = new CreditCardPaymentService();

    public static void main(String[] args) {
        DataInitializer.loadMockData();
        showMainMenu();
    }

    public static void showMainMenu() {
        while (true) {
            System.out.println("\n=== MOVIE TICKET SYSTEM ===");
            System.out.println("1. List Movies & Buy Ticket");
            System.out.println("2. Cancel Ticket (Refund)");
            System.out.println("3. Exit");

            int choice = ConsoleHelper.getIntegerInput("Select Option");

            switch (choice) {
                case 1:
                    handleTicketBooking();
                    break;
                case 2:
                    handleRefund();
                    break;
                case 3:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void handleTicketBooking() {
        System.out.println("\n--- MOVIES IN VISION ---");
        List<Movie> movies = CinemaSystem.allMovies;
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i));
        }

        int movieIndex = ConsoleHelper.getIntegerInput("Select Movie") - 1;
        if (movieIndex < 0 || movieIndex >= movies.size()) {
            System.out.println("Invalid movie selection.");
            return;
        }
        Movie selectedMovie = movies.get(movieIndex);

        System.out.println("\n--- AVAILABLE SHOWTIMES ---");

        List<ShowTime> movieShowTimes = new java.util.ArrayList<>();
        for (ShowTime st : CinemaSystem.activeShowTimes) {
            if (st.getMovie().equals(selectedMovie)) {
                movieShowTimes.add(st);
            }
        }

        if (movieShowTimes.isEmpty()) {
            System.out.println("No showtimes available for this movie.");
            return;
        }

        for (int i = 0; i < movieShowTimes.size(); i++) {
            System.out.println((i + 1) + ". " + movieShowTimes.get(i));
        }

        int showChoice = ConsoleHelper.getIntegerInput("Select ShowTime");
        if (showChoice < 1 || showChoice > movieShowTimes.size()) {
            System.out.println("Invalid showtime.");
            return;
        }
        ShowTime selectedShow = movieShowTimes.get(showChoice - 1);

        ConsoleHelper.printSeatMap(selectedShow);

        int row = ConsoleHelper.getIntegerInput("Enter Row");
        int col = ConsoleHelper.getIntegerInput("Enter Column/Seat No");

        Seat selectedSeat = selectedShow.getSeat(row, col);
        if (selectedSeat == null) {
            System.out.println("Invalid seat number.");
            return;
        }

        String name = ConsoleHelper.getStringInput("Enter Name");
        String surname = ConsoleHelper.getStringInput("Enter Surname");
        String email = ConsoleHelper.getEmailInput("Enter Email");
        String phoneNumber = ConsoleHelper.getPhoneNumberInput("Enter Phone Number");
        int birthYear = ConsoleHelper.getBirthYearInput("Enter Birth Year");

        Customer customer = new Customer(name, surname, email, phoneNumber, birthYear);

        System.out.println("Are you a Student? (1: Yes, 2: No)");
        int discountChoice = ConsoleHelper.getIntegerInput("Choice");

        PriceStrategy strategy = new StandardPriceStrategy();
        if (discountChoice == 1) {
            strategy = new StudentStrategy();
        }

        try {
            Ticket ticket = bookingManager.createTicket(customer, selectedShow, selectedSeat, strategy, paymentService);
            System.out.println("\n*** BOOKING SUCCESSFUL ***");
            ticket.printTicketInfo();
        } catch (SeatOccupiedException | AgeLimitException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleRefund() {
        String pnr = ConsoleHelper.getStringInput("Enter PNR Code to Refund");
        try {
            boolean result = refundService.processRefund(pnr);
            if (result) {
                System.out.println("Refund processed successfully.");
            } else {
                System.out.println("Refund failed. Show has passed.");
            }
        } catch (InvalidPNRException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
