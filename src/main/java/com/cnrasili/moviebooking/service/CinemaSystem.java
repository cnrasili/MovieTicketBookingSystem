package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.model.CinemaBranch;
import com.cnrasili.moviebooking.model.Movie;
import com.cnrasili.moviebooking.model.ShowTime;
import com.cnrasili.moviebooking.model.Ticket;
import java.util.ArrayList;
import java.util.List;

public class CinemaSystem {
    public static List<CinemaBranch> branches = new ArrayList<>();
    public static List<Movie> allMovies = new ArrayList<>();
    public static List<Ticket> soldTickets = new ArrayList<>();
    public static List<ShowTime> activeShowTimes = new ArrayList<>();

    public static Ticket searchTicketByPNR(String pnr) {
        for (Ticket ticket : soldTickets) {
            if (ticket.getPnrCode().equals(pnr)) {
                return ticket;
            }
        }
        return null;
    }
}
