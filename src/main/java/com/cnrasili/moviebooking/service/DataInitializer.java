package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Utility class used to seed the application with initial data.
 * <p>
 * This class populates the {@link CinemaSystem} by reading data from external CSV files:
 * <ul>
 * <li><b>movies.csv:</b> Loads movie details (Name, Duration, Price, Genre, Rating, Type).</li>
 * <li><b>branches.csv:</b> Loads cinema branches (Name, City, District).</li>
 * </ul>
 * It also generates a comprehensive showtime schedule for the next 5 days based on the loaded data.
 * </p>
 *
 * @author cnrasili
 * @version 1.2
 */
public class DataInitializer {

    private static final String MOVIES_FILE = "movies.csv";
    private static final String BRANCHES_FILE = "branches.csv";

    /**
     * Clears existing data and loads a fresh set of sample data from CSV files into the system.
     */
    public static void loadMockData() {
        CinemaSystem.allMovies.clear();
        CinemaSystem.branches.clear();
        CinemaSystem.activeShowTimes.clear();
        CinemaSystem.soldTickets.clear();

        loadMoviesFromCSV(MOVIES_FILE);
        loadBranchesFromCSV(BRANCHES_FILE);

        generateShowTimes();
    }

    /**
     * Reads movie data from the specified CSV file and populates the system.
     * <p>
     * Expected CSV Format: {@code Name, Duration, Price, Genre, AgeRating, Type}
     * <br>
     * <b>Logic:</b> It reads the 'Type' column (index 5) to instantiate either {@link Movie3D} or {@link Movie2D}.
     * </p>
     *
     * @param filePath The path to the CSV file.
     */
    private static void loadMoviesFromCSV(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if (data.length < 6) continue;

                String name = data[0].trim();
                int duration = Integer.parseInt(data[1].trim());
                double price = Double.parseDouble(data[2].trim());
                Genre genre = Genre.valueOf(data[3].trim());
                AgeRating rating = AgeRating.valueOf(data[4].trim());

                String type = data[5].trim().toUpperCase();

                Movie movie;
                if (type.equals("3D")) {
                    movie = new Movie3D(name, duration, price, genre, rating);
                } else {
                    movie = new Movie2D(name, duration, price, genre, rating);
                }

                CinemaSystem.allMovies.add(movie);
            }
            System.out.println("INFO: Movies loaded successfully from " + filePath);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ERROR: Failed to load movies from CSV. " + e.getMessage());
        }
    }

    /**
     * Reads branch data from the specified CSV file and populates the system.
     * <p>
     * Expected CSV Format: {@code BranchName, City, District}
     * </p>
     *
     * @param filePath The path to the CSV file.
     */
    private static void loadBranchesFromCSV(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");
                if (data.length < 3) continue;

                String name = data[0].trim();
                String city = data[1].trim();
                String district = data[2].trim();

                CinemaBranch branch = new CinemaBranch(name, city, district);

                branch.addHall(new IMAXHall("IMAX Saloon", 6, 8));
                branch.addHall(new VIPHall("Gold Class VIP", 4, 4));
                branch.addHall(new StandardHall("Standard Saloon", 5, 6));

                CinemaSystem.branches.add(branch);
            }
            System.out.println("INFO: Branches loaded successfully from " + filePath);
        } catch (IOException e) {
            System.err.println("ERROR: Failed to load branches from CSV. " + e.getMessage());
        }
    }

    /**
     * Generates showtimes for the next 5 days based on loaded movies and branches.
     */
    private static void generateShowTimes() {
        List<Movie> movies = CinemaSystem.allMovies;
        if (movies.isEmpty()) {
            System.out.println("WARNING: No movies loaded. Skipping showtime generation.");
            return;
        }

        Movie m1 = movies.size() > 0 ? movies.get(0) : null;
        Movie m2 = movies.size() > 1 ? movies.get(1) : null;
        Movie m3 = movies.size() > 2 ? movies.get(2) : null;
        Movie m4 = movies.size() > 3 ? movies.get(3) : null;

        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        for (CinemaBranch branch : CinemaSystem.branches) {
            CinemaHall imaxHall = null;
            CinemaHall vipHall = null;
            CinemaHall stdHall = null;

            for (CinemaHall hall : branch.getHalls()) {
                if (hall instanceof IMAXHall) imaxHall = hall;
                else if (hall instanceof VIPHall) vipHall = hall;
                else if (hall instanceof StandardHall) stdHall = hall;
            }

            if (imaxHall == null || vipHall == null || stdHall == null) continue;

            for (int i = 0; i < 5; i++) {
                LocalDateTime currentDate = tomorrow.plusDays(i);

                if (m1 != null) {
                    addShowTime(currentDate, 10, 0, m1, stdHall);
                    addShowTime(currentDate, 14, 0, m1, vipHall);
                    addShowTime(currentDate, 18, 0, m1, imaxHall);
                }
                if (m2 != null) {
                    addShowTime(currentDate, 10, 30, m2, stdHall);
                    addShowTime(currentDate, 14, 30, m2, vipHall);
                    addShowTime(currentDate, 18, 30, m2, imaxHall);
                }
                if (m4 != null) {
                    addShowTime(currentDate, 9, 30, m4, stdHall);
                    addShowTime(currentDate, 11, 30, m4, vipHall);
                    addShowTime(currentDate, 13, 30, m4, imaxHall);
                }
                if (m3 != null) {
                    addShowTime(currentDate, 15, 30, m3, stdHall);
                    addShowTime(currentDate, 18, 0, m3, vipHall);
                    addShowTime(currentDate, 20, 30, m3, imaxHall);
                }
            }
        }
    }

    private static void addShowTime(LocalDateTime baseDate, int hour, int minute, Movie movie, CinemaHall hall) {
        LocalDateTime sessionTime = baseDate.withHour(hour).withMinute(minute);
        ShowTime showTime = new ShowTime(sessionTime, movie, hall);
        CinemaSystem.activeShowTimes.add(showTime);
    }
}
