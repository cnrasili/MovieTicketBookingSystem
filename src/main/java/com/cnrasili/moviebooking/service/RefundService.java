package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.InvalidPNRException;
import com.cnrasili.moviebooking.model.Ticket;
import java.time.LocalDateTime;

public class RefundService {

    public boolean processRefund(String pnrCode) throws InvalidPNRException {
        Ticket ticket = CinemaSystem.searchTicketByPNR(pnrCode);

        if (ticket == null) {
            throw new InvalidPNRException("Refund Failed: Invalid PNR Code (" + pnrCode + ")");
        }

        if (ticket.getShowTime().getTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        ticket.getSeat().cancel();

        CinemaSystem.soldTickets.remove(ticket);

        return true;
    }
}
