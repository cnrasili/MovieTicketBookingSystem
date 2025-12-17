package com.cnrasili.moviebooking;

import com.cnrasili.moviebooking.exception.AgeLimitException;
import com.cnrasili.moviebooking.exception.InvalidPNRException;
import com.cnrasili.moviebooking.exception.SeatOccupiedException;
import com.cnrasili.moviebooking.model.*;
import com.cnrasili.moviebooking.service.*;
import com.cnrasili.moviebooking.util.ConsoleHelper;
import com.cnrasili.moviebooking.exception.PaymentFailedException;
import java.util.ArrayList;
import java.util.List;

/**
 * The entry point of the Cinema Booking Application.
 * <p>
 * This class handles the main user interface loop, allowing users to:
 * <ul>
 * <li>Browse cinema branches and movies.</li>
 * <li>Book tickets with dynamic pricing strategies.</li>
 * <li>Cancel tickets and process refunds.</li>
 * </ul>
 * It orchestrates the interaction between the User, {@link BookingManager}, and {@link ConsoleHelper}.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class Main {
    private static final BookingManager bookingManager = new BookingManager();
    private static final RefundService refundService = new RefundService();
    private static final PaymentService paymentService = new CreditCardPaymentService();

    /**
     * Main method that initializes the system and starts the application loop.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        DataInitializer.loadMockData();
        showMainMenu();
    }

    /**
     * Displays the main menu options and routes user input to specific handlers.
     * <p>
     * Options:
     * <ol>
     * <li>Buy Ticket: Triggers {@link #handleTicketBooking()}.</li>
     * <li>Refund: Triggers {@link #handleRefund()}.</li>
     * <li>Exit: Terminates the application.</li>
     * </ol>
     * </p>
     */
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

    /**
     * Orchestrates the step-by-step ticket booking process.
     * <p>
     * Implements a state-machine logic where 'step' variable controls the flow:
     * <ul>
     * <li>Step 1: Select Branch</li>
     * <li>Step 2: Select Movie</li>
     * <li>Step 3: Select Showtime (filtered by branch & movie)</li>
     * <li>Step 4: Select Seat (visual map displayed)</li>
     * <li>Step 5: Enter Customer Details</li>
     * <li>Step 6: Payment & Finalization (Student check, Card processing)</li>
     * </ul>
     * Allows users to go back to previous steps by entering '0'.
     * </p>
     */
    private static void handleTicketBooking() {
        int step = 1;

        // Temporary storage for user selections during the flow
        CinemaBranch selectedBranch = null;
        Movie selectedMovie = null;
        ShowTime selectedShow = null;
        Seat selectedSeat = null;
        Customer customer = null;
        PriceStrategy strategy = null;

        while (step != 0) {
            switch (step) {
                case 1: // Branch Selection
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

                case 2: // Movie Selection
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

                case 3: // Showtime Selection
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
                        ShowTime st = filteredShowTimes.get(i);
                        String displayString = (i + 1) + ". " + st.toString();
                        if (bookingManager.isFirstSession(st)) {
                            displayString += "(FIRST SESSION)";
                        }
                        System.out.println(displayString);
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

                case 4: // Seat Selection
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

                case 5: // Customer Details
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

                case 6: // Payment & Finalization
                    System.out.println("\n--- DISCOUNT & PAYMENT ---");
                    System.out.println("Are you a Student? (1: Yes, 2: No)");
                    System.out.println("0. Go Back");

                    int discountChoice = ConsoleHelper.getIntegerInput("Choice");
                    if (discountChoice == 0) {
                        step--;
                        break;
                    }

                    strategy = new StandardPriceStrategy();

                    if (discountChoice == 1) {
                        System.out.println("--- STUDENT VERIFICATION ---");
                        String studentId = ConsoleHelper.getStringInput("Enter Student ID Card No (e.g., 1111) or '0' to Skip");

                        if (!studentId.equals("0")) {
                            StudentService studentService = new StudentService();

                            if (studentService.validateStudentId(studentId)) {
                                System.out.println(">> Success: Student ID Verified! Discount will be applied.");
                                strategy = new StudentStrategy();
                            } else {
                                System.out.println(">> Error: Invalid Student ID! Proceeding with Standard Price.");
                            }
                        } else {
                            System.out.println(">> Student verification skipped. Standard price applies.");
                        }
                    }

                    String cardNumber = ConsoleHelper.getCardNumberInput("Enter Card Number (16 digits) or '0' to Back");

                    if (cardNumber.equals("0")) {
                        break;
                    }

                    try {
                        Ticket ticket = bookingManager.createTicket(customer, selectedShow, selectedSeat, strategy, paymentService, cardNumber);

                        System.out.println("\n*** BOOKING SUCCESSFUL ***");
                        ticket.printTicketInfo();
                        return; // Booking complete, return to main menu

                    } catch (SeatOccupiedException | AgeLimitException | PaymentFailedException e) {
                        System.out.println("Error: " + e.getMessage());
                        // Handle errors by sending user back to relevant step
                        if (e instanceof SeatOccupiedException) step = 4; // Reselect seat
                        else if (e instanceof AgeLimitException) step = 2; // Reselect movie
                    }
                    break;
            }
        }
    }

    /**
     * Handles the ticket cancellation process.
     * <p>
     * Prompts the user for a PNR code and delegates the logic to {@link RefundService}.
     * </p>
     */
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
