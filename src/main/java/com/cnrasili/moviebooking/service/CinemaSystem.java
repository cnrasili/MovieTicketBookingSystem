package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.model.CinemaBranch;
import com.cnrasili.moviebooking.model.Movie;
import com.cnrasili.moviebooking.model.ShowTime;
import com.cnrasili.moviebooking.model.Ticket;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Acts as the centralized in-memory database for the entire application.
 * <p>
 * This class holds static data structures acting as database tables.
 * The data is populated at startup by {@link DataInitializer} reading from external CSV files.
 * <br>
 * stored Data Includes:
 * <ul>
 * <li><b>Cinema Network:</b> Branches, Halls, and Movies (from CSV).</li>
 * <li><b>Operations:</b> Active Showtimes (generated dynamically) and Sold Tickets (history).</li>
 * <li><b>Mock External Systems:</b> Credit Card balances and Valid Student IDs (from CSV).</li>
 * </ul>
 * </p>
 *
 * @author cnrasili
 * @version 2.0
 */
public class CinemaSystem {

    /** List of all cinema branches in the chain. Populated from {@code branches.csv}. */
    public static List<CinemaBranch> branches = new ArrayList<>();

    /** List of all movies currently available in the system. Populated from {@code movies.csv}. */
    public static List<Movie> allMovies = new ArrayList<>();

    /** Registry of all tickets sold within the system. Used for reporting and refunds. */
    public static List<Ticket> soldTickets = new ArrayList<>();

    /** List of all active showtimes (sessions) available for booking. */
    public static List<ShowTime> activeShowTimes = new ArrayList<>();

    /** * Simulates an external banking database.
     * <p>Key: 16-digit Card Number, Value: Current Balance.</p>
     * Populated from {@code credit_cards.csv}.
     */
    public static Map<String, Double> mockCardDB = new HashMap<>();

    /** * Registry of valid student IDs eligible for discounts.
     * Populated from {@code students.csv}.
     */
    public static List<String> validStudentIds = new ArrayList<>();

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
