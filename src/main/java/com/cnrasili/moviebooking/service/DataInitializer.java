package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.model.*;
import java.time.LocalDateTime;

public class DataInitializer {

    public static void loadMockData() {

        Movie avatar = new Movie3D("Avatar 2", 192, 100.0, Genre.SCI_FI, AgeRating.PLUS_13);
        Movie titanic = new Movie2D("Titanic", 195, 80.0, Genre.ROMANCE, AgeRating.PLUS_13);
        Movie joker = new Movie2D("Joker", 122, 90.0, Genre.DRAMA, AgeRating.PLUS_18);
        Movie minions = new Movie2D("Minions", 95, 70.0, Genre.COMEDY, AgeRating.GENERAL_AUDIENCE);

        CinemaSystem.allMovies.add(avatar);
        CinemaSystem.allMovies.add(titanic);
        CinemaSystem.allMovies.add(joker);
        CinemaSystem.allMovies.add(minions);

        CinemaBranch mainBranch = new CinemaBranch("Grand Pera", "Istanbul", "Beyoglu");
        CinemaHall imax = new IMAXHall("IMAX Hall", 5, 5);
        CinemaHall standard = new StandardHall("Standard Hall", 4, 4);

        mainBranch.addHall(imax);
        mainBranch.addHall(standard);
        CinemaSystem.branches.add(mainBranch);

        for (Movie movie : CinemaSystem.allMovies) {
            CinemaSystem.activeShowTimes.add(new ShowTime(
                    LocalDateTime.now().plusDays(1).withHour(10).withMinute(0), movie, standard));

            CinemaSystem.activeShowTimes.add(new ShowTime(
                    LocalDateTime.now().plusDays(1).withHour(14).withMinute(0), movie, imax));

            CinemaSystem.activeShowTimes.add(new ShowTime(
                    LocalDateTime.now().plusDays(1).withHour(18).withMinute(0), movie, standard));
        }
    }
}
