package com.cnrasili.moviebooking.util;

import com.cnrasili.moviebooking.model.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Utility class handling all console-based input and output operations.
 * <p>
 * This class provides static methods to:
 * <ul>
 * <li>Visualize the cinema hall seating map.</li>
 * <li>Safely read user inputs (Integer, String) preventing crashes.</li>
 * <li>Validate specific formats like Email, Phone Number, and Credit Card using Regex.</li>
 * </ul>
 * It isolates the user interface logic from the business logic.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class ConsoleHelper {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prints the visual seating map of a specific showtime to the console.
     * <p>
     * Displays a "SCREEN" header and iterates through rows and columns.
     * Uses formatted output (printf) to align seats perfectly.
     * <ul>
     * <li>Standard Seats are shown as 5-character boxes (e.g., [ 1 ] or [ X ]).</li>
     * <li>LoveSeats are shown as 9-character boxes (e.g., [  1  ] or [ XX ]).</li>
     * </ul>
     * </p>
     *
     * @param showTime The showtime session whose seat map is to be displayed.
     */
    public static void printSeatMap(ShowTime showTime) {
        CinemaHall hall = showTime.getHall();
        int totalRows = hall.getTotalRows();
        int totalCols = hall.getTotalCols();

        System.out.println("\n      ================ SCREEN =================");

        for (int row = 1; row <= totalRows; row++) {

            System.out.printf("Row %-2d: ", row);

            for (int col = 1; col <= totalCols; col++) {
                Seat seat = showTime.getSeat(row, col);

                if (seat != null) {

                    boolean isLoveSeat = seat instanceof LoveSeat;
                    int width = isLoveSeat ? 9 : 5;

                    String content;

                    if (seat.getStatus() == SeatStatus.BOOKED) {
                        content = isLoveSeat ? "X X" : "X";
                    } else {
                        content = String.valueOf(seat.getNumber());
                    }

                    String fmt = "[%-" + (width - 2) + "s] ";

                    System.out.printf(fmt, padCenter(content, width - 2));
                }
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------");
    }

    /**
     * Helper method to center a string within a given width.
     * <p>
     * Used for aligning seat numbers or status markers inside the visual boxes.
     * </p>
     *
     * @param s     The string to center.
     * @param width The total width to fill.
     * @return The padded, centered string.
     */
    private static String padCenter(String s, int width) {
        if (s == null || width <= s.length()) {
            return s;
        }
        StringBuilder sb = new StringBuilder(width);
        for (int i = 0; i < (width - s.length()) / 2; i++) {
            sb.append(' ');
        }
        sb.append(s);
        while (sb.length() < width) {
            sb.append(' ');
        }
        return sb.toString();
    }


    /**
     * Prompts the user for an integer input and ensures valid entry.
     * <p>
     * Loops until a valid integer is provided, preventing {@link java.util.InputMismatchException}.
     * </p>
     *
     * @param prompt The message displayed to the user.
     * @return A valid integer input.
     */
    public static int getIntegerInput(String prompt) {
        System.out.print(prompt + ": ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print(prompt + ": ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    /**
     * Prompts the user for a standard string input.
     *
     * @param prompt The message displayed to the user.
     * @return The text entered by the user.
     */
    public static String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    /**
     * Prompts the user for an email address and validates its format.
     * <p>
     * Uses Regex to ensure the input looks like an email (e.g., user@domain.com).
     * Loops until a valid format is entered.
     * </p>
     *
     * @param prompt The message displayed to the user.
     * @return A valid email string.
     */
    public static String getEmailInput(String prompt) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);

        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine();
            if (pattern.matcher(input).matches()) {
                return input;
            }
            System.out.println("Invalid email format. Please try again (e.g., user@mail.com).");
        }
    }

    /**
     * Prompts the user for a phone number and validates its format.
     * <p>
     * Accepts 10 or 11 digit numeric strings. Loops until valid.
     * </p>
     *
     * @param prompt The message displayed to the user.
     * @return A valid phone number string.
     */
    public static String getPhoneNumberInput(String prompt) {
        String phoneRegex = "^[0-9]{10,11}$";
        Pattern pattern = Pattern.compile(phoneRegex);

        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine();
            if (pattern.matcher(input).matches()) {
                return input;
            }
            System.out.println("Invalid phone number. Please enter 10 or 11 digits.");
        }
    }

    /**
     * Prompts the user for a birth year and checks for logical validity.
     * <p>
     * The year must be greater than 1900 and less than or equal to the current year.
     * </p>
     *
     * @param prompt The message displayed to the user.
     * @return A valid year (int).
     */
    public static int getBirthYearInput(String prompt) {
        int currentYear = LocalDateTime.now().getYear();
        while (true) {
            int year = getIntegerInput(prompt);
            if (year > 1900 && year <= currentYear) {
                return year;
            }
            System.out.println("Invalid year. Please enter a realistic birth year (1900 - " + currentYear + ").");
        }
    }

    /**
     * Prompts the user for a 16-digit credit card number.
     * <p>
     * Loops until the input matches exactly 16 digits.
     * Returns "0" immediately if the user wishes to cancel (if "0" logic is used upstream).
     * </p>
     *
     * @param prompt The message displayed to the user.
     * @return A valid 16-digit card number string.
     */
    public static String getCardNumberInput(String prompt) {
        String cardRegex = "^[0-9]{16}$";
        Pattern pattern = Pattern.compile(cardRegex);

        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                return "0";
            }

            if (pattern.matcher(input).matches()) {
                return input;
            }
            System.out.println("Invalid card number. Please enter 16 digits (no spaces).");
        }
    }
}
