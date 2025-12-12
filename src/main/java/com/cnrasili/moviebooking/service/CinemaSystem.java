package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.model.CinemaBranch;
import com.cnrasili.moviebooking.model.Movie;
import com.cnrasili.moviebooking.model.ShowTime;
import com.cnrasili.moviebooking.model.Ticket;
import java.util.ArrayList;
import java.util.List;

/**
 * Acts as the centralized in-memory database for the entire application.
 * <p>
 * This class holds static lists representing database tables for:
 * <ul>
 * <li>Branches and Halls</li>
 * <li>Movies in vision</li>
 * <li>Active Showtimes</li>
 * <li>Sold Tickets (History)</li>
 * </ul>
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class CinemaSystem {
    /** List of all cinema branches in the chain. */
    public static List<CinemaBranch> branches = new ArrayList<>();

    /** List of all movies currently available in the system. */
    public static List<Movie> allMovies = new ArrayList<>();

    /** Registry of all tickets sold within the system. */
    public static List<Ticket> soldTickets = new ArrayList<>();

    /** List of all active showtimes (sessions) available for booking. */
    public static List<ShowTime> activeShowTimes = new ArrayList<>();

    /**
     * Searches for a ticket in the system using its PNR code.
     *
     * @param pnr The unique Passenger Name Record code.
     * @return The {@link Ticket} object if found; {@code null} otherwise.
     */
    public static Ticket searchTicketByPNR(String pnr) {
        for (Ticket ticket : soldTickets) {
            if (ticket.getPnrCode().equals(pnr)) {
                return ticket;
            }
        }
        return null;
    }
}
