package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.model.*;
import java.time.LocalDateTime;

public class DataInitializer {

    public static void loadMockData() {
        Movie avatar = new Movie3D("Avatar: The Way of Water", 192, 100.0, Genre.SCI_FI, AgeRating.PLUS_13);
        Movie titanic = new Movie2D("Titanic", 195, 80.0, Genre.ROMANCE, AgeRating.PLUS_13);
        Movie joker = new Movie2D("Joker", 122, 90.0, Genre.DRAMA, AgeRating.PLUS_18);
        Movie minions = new Movie2D("Minions", 95, 70.0, Genre.COMEDY, AgeRating.GENERAL_AUDIENCE);

        CinemaSystem.allMovies.add(avatar);
        CinemaSystem.allMovies.add(titanic);
        CinemaSystem.allMovies.add(joker);
        CinemaSystem.allMovies.add(minions);

        CinemaBranch mainBranch = new CinemaBranch("Grand Pera", "Istanbul", "Beyoglu");

        CinemaHall hall1 = new IMAXHall("Hall 1 (IMAX)", 5, 5);
        CinemaHall hall2 = new StandardHall("Hall 2", 4, 4);

        mainBranch.addHall(hall1);
        mainBranch.addHall(hall2);

        CinemaSystem.branches.add(mainBranch);

        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).withHour(14).withMinute(0);
        LocalDateTime nextDay = LocalDateTime.now().plusDays(2).withHour(20).withMinute(0);

        ShowTime show1 = new ShowTime(tomorrow, avatar, hall1);
        ShowTime show2 = new ShowTime(nextDay, joker, hall2);

        CinemaSystem.activeShowTimes.add(show1);
        CinemaSystem.activeShowTimes.add(show2);
    }
}
