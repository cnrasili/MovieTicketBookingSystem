package com.cnrasili.moviebooking.util;

import com.cnrasili.moviebooking.model.Seat;
import com.cnrasili.moviebooking.model.ShowTime;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static void printSeatMap(ShowTime showTime) {
        System.out.println("\n--- SCREEN ---");
        int totalRows = showTime.getHall().getTotalRows();
        int totalCols = showTime.getHall().getTotalCols();

        for (int row = 1; row <= totalRows; row++) {
            System.out.print("Row " + row + ": ");
            for (int col = 1; col <= totalCols; col++) {
                Seat seat = showTime.getSeat(row, col);
                if (seat != null) {
                    System.out.print(seat.toString() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------\n");
    }

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

    public static String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

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
}
