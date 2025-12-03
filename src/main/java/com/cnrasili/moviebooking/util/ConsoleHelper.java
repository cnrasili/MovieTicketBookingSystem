package com.cnrasili.moviebooking.util;

import com.cnrasili.moviebooking.model.Seat;
import com.cnrasili.moviebooking.model.ShowTime;
import java.util.Scanner;

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
        return scanner.nextInt();
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.next();
    }
}
