package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.InvalidPNRException;
import com.cnrasili.moviebooking.model.Ticket;
import java.time.LocalDateTime;

/**
 * Handles ticket cancellation and refund operations.
 * <p>
 * This service ensures that refunds are only processed for valid tickets
 * and for showtimes that have not yet started. It also handles freeing up
 * the booked seat so it can be sold again.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class RefundService {

    /**
     * Processes a refund request for a given PNR code.
     * <p>
     * The refund logic enforces the following rules:
     * <ol>
     * <li>The PNR code must match a valid, sold ticket.</li>
     * <li>The showtime must be in the future (tickets for past shows cannot be refunded).</li>
     * </ol>
     * If successful, the seat is released (set to AVAILABLE) and the ticket is removed from the system.
     * </p>
     *
     * @param pnrCode The unique Passenger Name Record of the ticket to be refunded.
     * @return {@code true} if the refund was successful; {@code false} if the show has already started.
     * @throws InvalidPNRException If the PNR code does not exist in the system.
     */
    public boolean processRefund(String pnrCode) throws InvalidPNRException {
        Ticket ticket = CinemaSystem.searchTicketByPNR(pnrCode);

        if (ticket == null) {
            throw new InvalidPNRException("Refund Failed: Invalid PNR Code (" + pnrCode + ")");
        }

        if (ticket.getShowTime().getTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        ticket.getSeat().cancelBooking();

        CinemaSystem.soldTickets.remove(ticket);

        return true;
    }
}
