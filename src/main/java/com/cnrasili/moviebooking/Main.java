package com.cnrasili.moviebooking;

import com.cnrasili.moviebooking.exception.AgeLimitException;
import com.cnrasili.moviebooking.exception.InvalidPNRException;
import com.cnrasili.moviebooking.exception.SeatOccupiedException;
import com.cnrasili.moviebooking.model.*;
import com.cnrasili.moviebooking.service.*;
import com.cnrasili.moviebooking.util.ConsoleHelper;
import java.util.ArrayList;

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
        int step = 1;

        CinemaBranch selectedBranch = null;
        Movie selectedMovie = null;
        ShowTime selectedShow = null;
        Seat selectedSeat = null;
        Customer customer = null;
        PriceStrategy strategy = null;

        while (step != 0) {
            switch (step) {
                case 1:
                    System.out.println("\n--- SELECT CINEMA BRANCH ---");
                    List<CinemaBranch> branches = CinemaSystem.branches;
                    for (int i = 0; i < branches.size(); i++) {
                        System.out.println((i + 1) + ". " + branches.get(i));
                    }
                    System.out.println("0. Back to Main Menu");

                    int branchIndex = ConsoleHelper.getIntegerInput("Select Branch");
                    if (branchIndex == 0) {
                        return;
                    }
                    if (branchIndex < 1 || branchIndex > branches.size()) {
                        System.out.println("Invalid selection.");
                    } else {
                        selectedBranch = branches.get(branchIndex - 1);
                        step++;
                    }
                    break;

                case 2:
                    System.out.println("\n--- MOVIES IN VISION ---");
                    List<Movie> movies = CinemaSystem.allMovies;
                    for (int i = 0; i < movies.size(); i++) {
                        System.out.println((i + 1) + ". " + movies.get(i));
                    }
                    System.out.println("0. Go Back (Branch Selection)");

                    int movieIndex = ConsoleHelper.getIntegerInput("Select Movie");
                    if (movieIndex == 0) {
                        step--;
                    } else if (movieIndex < 1 || movieIndex > movies.size()) {
                        System.out.println("Invalid movie selection.");
                    } else {
                        selectedMovie = movies.get(movieIndex - 1);
                        step++;
                    }
                    break;

                case 3:
                    System.out.println("\n--- AVAILABLE SHOWTIMES (" + selectedBranch.getName() + ") ---");

                    List<ShowTime> filteredShowTimes = new ArrayList<>();
                    for (ShowTime st : CinemaSystem.activeShowTimes) {
                        if (st.getMovie().equals(selectedMovie) && selectedBranch.getHalls().contains(st.getHall())) {
                            filteredShowTimes.add(st);
                        }
                    }

                    if (filteredShowTimes.isEmpty()) {
                        System.out.println("No showtimes available for this movie at this branch.");
                        System.out.println("0. Go Back");
                        int back = ConsoleHelper.getIntegerInput("Choice");
                        if(back == 0) step--;
                        break;
                    }

                    for (int i = 0; i < filteredShowTimes.size(); i++) {
                        System.out.println((i + 1) + ". " + filteredShowTimes.get(i));
                    }
                    System.out.println("0. Go Back (Movie Selection)");

                    int showChoice = ConsoleHelper.getIntegerInput("Select ShowTime");
                    if (showChoice == 0) {
                        step--;
                    } else if (showChoice < 1 || showChoice > filteredShowTimes.size()) {
                        System.out.println("Invalid showtime.");
                    } else {
                        selectedShow = filteredShowTimes.get(showChoice - 1);
                        step++;
                    }
                    break;

                case 4:
                    ConsoleHelper.printSeatMap(selectedShow);
                    System.out.println("Enter 0 in Row to Go Back");

                    int row = ConsoleHelper.getIntegerInput("Enter Row");
                    if (row == 0) {
                        step--;
                        break;
                    }

                    int col = ConsoleHelper.getIntegerInput("Enter Column");
                    Seat seatCandidate = selectedShow.getSeat(row, col);

                    if (seatCandidate == null) {
                        System.out.println("Invalid seat number.");
                    } else if (!seatCandidate.isAvailable()) {
                        System.out.println("Seat is already occupied!");
                    } else {
                        selectedSeat = seatCandidate;
                        step++;
                    }
                    break;

                case 5:
                    System.out.println("\n--- CUSTOMER DETAILS ---");
                    String name = ConsoleHelper.getStringInput("Enter Name (or '0' to Back)");
                    if (name.equals("0")) {
                        step--;
                        break;
                    }

                    String surname = ConsoleHelper.getStringInput("Enter Surname");
                    String email = ConsoleHelper.getEmailInput("Enter Email");
                    String phone = ConsoleHelper.getPhoneNumberInput("Enter Phone Number");
                    int birthYear = ConsoleHelper.getBirthYearInput("Enter Birth Year");

                    customer = new Customer(name, surname, email, phone, birthYear);
                    step++;
                    break;

                case 6:
                    System.out.println("\n--- DISCOUNT & PAYMENT ---");
                    System.out.println("Are you a Student? (1: Yes, 2: No)");
                    System.out.println("0. Go Back");

                    int discountChoice = ConsoleHelper.getIntegerInput("Choice");
                    if (discountChoice == 0) {
                        step--;
                        break;
                    }

                    strategy = (discountChoice == 1) ? new StudentStrategy() : new StandardPriceStrategy();

                    String cardNumber = ConsoleHelper.getCardNumberInput("Enter Card Number (16 digits) or '0' to Back");
                    if (cardNumber.equals("0")) {
                        break;
                    }

                    try {
                        Ticket ticket = bookingManager.createTicket(customer, selectedShow, selectedSeat, strategy, paymentService, cardNumber);
                        if (ticket != null) {
                            System.out.println("\n*** BOOKING SUCCESSFUL ***");
                            ticket.printTicketInfo();
                            return;
                        } else {
                            System.out.println("Booking failed due to payment error.");
                        }
                    } catch (SeatOccupiedException | AgeLimitException e) {
                        System.out.println("Error: " + e.getMessage());
                        if (e instanceof SeatOccupiedException) step = 4;
                        else if (e instanceof AgeLimitException) step = 2;
                    }
                    break;
            }
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
