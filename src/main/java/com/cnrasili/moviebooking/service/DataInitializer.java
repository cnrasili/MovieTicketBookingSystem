package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class used to seed the application with initial mock data.
 * <p>
 * This class populates the {@link CinemaSystem} with:
 * <ul>
 * <li>Movies (2D, 3D)</li>
 * <li>Cinema Branches and Halls (Standard, IMAX, VIP)</li>
 * <li>Showtimes linked to specific times and halls</li>
 * </ul>
 * It is typically called once at the application startup.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class DataInitializer {

    /**
     * Clears existing data and loads a fresh set of sample data into the system.
     * <p>
     * Populates the system with:
     * <ul>
     * <li><b>Movies:</b> Avatar 2, Titanic, Joker, Minions.</li>
     * <li><b>Branches:</b> 4 Major Locations (Marmarapark, Capacity, Akasya, Torun Center).</li>
     * <li><b>Showtimes:</b> A comprehensive schedule covering the <b>next 5 days</b> (starting tomorrow) to simulate a real-world weekly plan.</li>
     * </ul>
     * </p>
     */
    public static void loadMockData() {
        CinemaSystem.allMovies.clear();
        CinemaSystem.branches.clear();
        CinemaSystem.activeShowTimes.clear();
        CinemaSystem.soldTickets.clear();

        Movie avatar = new Movie3D("Avatar 2", 192, 100.0, Genre.SCI_FI, AgeRating.PLUS_13);
        Movie titanic = new Movie2D("Titanic", 195, 80.0, Genre.ROMANCE, AgeRating.PLUS_13);
        Movie joker = new Movie2D("Joker", 122, 80.0, Genre.DRAMA, AgeRating.PLUS_18);
        Movie minions = new Movie2D("Minions", 95, 80.0, Genre.COMEDY, AgeRating.GENERAL_AUDIENCE);

        CinemaSystem.allMovies.add(avatar);
        CinemaSystem.allMovies.add(titanic);
        CinemaSystem.allMovies.add(joker);
        CinemaSystem.allMovies.add(minions);

        List<CinemaBranch> branchList = new ArrayList<>();
        branchList.add(new CinemaBranch("Paribu Cineverse Marmarapark", "İstanbul", "Esenyurt"));
        branchList.add(new CinemaBranch("Paribu Cineverse Capacity", "İstanbul", "Bakırköy"));
        branchList.add(new CinemaBranch("Paribu Cineverse Akasya", "İstanbul", "Üsküdar"));
        branchList.add(new CinemaBranch("Paribu Cineverse Cevahir", "İstanbul", "Şişli"));

        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        for (CinemaBranch branch : branchList) {

            CinemaHall imaxHall = new IMAXHall("IMAX Saloon", 6, 8);
            CinemaHall vipHall = new VIPHall("Gold Class VIP", 4, 4);
            CinemaHall stdHall = new StandardHall("Standard Saloon", 5, 6);

            branch.addHall(imaxHall);
            branch.addHall(vipHall);
            branch.addHall(stdHall);

            CinemaSystem.branches.add(branch);

            for (int i = 0; i < 5; i++) {

                LocalDateTime currentDate = tomorrow.plusDays(i);

                addShowTime(currentDate, 10, 0, avatar, stdHall);
                addShowTime(currentDate, 14, 0, avatar, vipHall);
                addShowTime(currentDate, 18, 0, avatar, imaxHall);

                addShowTime(currentDate, 10, 30, titanic, stdHall);
                addShowTime(currentDate, 14, 30, titanic, vipHall);
                addShowTime(currentDate, 18, 30, titanic, imaxHall);

                addShowTime(currentDate, 9, 30, minions, stdHall);
                addShowTime(currentDate, 11, 30, minions, vipHall);
                addShowTime(currentDate, 13, 30, minions, imaxHall);

                addShowTime(currentDate, 15, 30, joker, stdHall);
                addShowTime(currentDate, 18, 0, joker, vipHall);
                addShowTime(currentDate, 20, 30, joker, imaxHall);
            }
        }
    }

    /**
     * Helper method to create and register a new Showtime.
     *
     * @param baseDate The date of the show.
     * @param hour     The hour of the show.
     * @param minute   The minute of the show.
     * @param movie    The movie to be shown.
     * @param hall     The hall where it will be screened.
     */
    private static void addShowTime(LocalDateTime baseDate, int hour, int minute, Movie movie, CinemaHall hall) {
        LocalDateTime sessionTime = baseDate.withHour(hour).withMinute(minute);
        ShowTime showTime = new ShowTime(sessionTime, movie, hall);
        CinemaSystem.activeShowTimes.add(showTime);
    }
}
