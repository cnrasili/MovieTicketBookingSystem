package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.model.*;
import java.time.LocalDateTime;

public class DataInitializer {

    public static void loadMockData() {
        CinemaSystem.allMovies.clear();
        CinemaSystem.branches.clear();
        CinemaSystem.activeShowTimes.clear();
        CinemaSystem.soldTickets.clear();

        Movie avatar = new Movie3D("Avatar 2", 192, 100.0, Genre.SCI_FI, AgeRating.PLUS_13);
        Movie titanic = new Movie2D("Titanic", 195, 80.0, Genre.ROMANCE, AgeRating.PLUS_13);
        Movie joker = new Movie2D("Joker", 122, 90.0, Genre.DRAMA, AgeRating.PLUS_18);
        Movie minions = new Movie2D("Minions", 95, 70.0, Genre.COMEDY, AgeRating.GENERAL_AUDIENCE);

        CinemaSystem.allMovies.add(avatar);
        CinemaSystem.allMovies.add(titanic);
        CinemaSystem.allMovies.add(joker);
        CinemaSystem.allMovies.add(minions);

        CinemaBranch branch1 = new CinemaBranch("Paribu Cineverse Marmarapark", "İstanbul", "Esenyurt");
        CinemaHall hall1_IMAX = new IMAXHall("IMAX Salon", 5, 5);
        CinemaHall hall1_Std = new StandardHall("Salon 2", 4, 6);
        branch1.addHall(hall1_IMAX);
        branch1.addHall(hall1_Std);

        CinemaBranch branch2 = new CinemaBranch("Paribu Cineverse Capacity", "İstanbul", "Bakırköy");
        CinemaHall hall2_VIP = new VIPHall("Gold Class VIP", 3, 4);
        CinemaHall hall2_Std = new StandardHall("Salon 3", 5, 5);
        branch2.addHall(hall2_VIP);
        branch2.addHall(hall2_Std);

        CinemaSystem.branches.add(branch1);
        CinemaSystem.branches.add(branch2);

        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);


        addShowTime(tomorrow, 14, 0, avatar, hall1_IMAX);
        addShowTime(tomorrow, 20, 0, joker, hall1_Std);
        addShowTime(tomorrow, 10, 0, minions, hall1_Std);

        addShowTime(tomorrow, 16, 30, titanic, hall2_VIP);
        addShowTime(tomorrow, 11, 0, avatar, hall2_Std);
    }

    private static void addShowTime(LocalDateTime baseDate, int hour, int minute, Movie movie, CinemaHall hall) {
        LocalDateTime sessionTime = baseDate.withHour(hour).withMinute(minute);
        ShowTime showTime = new ShowTime(sessionTime, movie, hall);
        CinemaSystem.activeShowTimes.add(showTime);
    }
}
