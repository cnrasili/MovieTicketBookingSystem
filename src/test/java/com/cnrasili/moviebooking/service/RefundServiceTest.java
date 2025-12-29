package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.InvalidPNRException;
import com.cnrasili.moviebooking.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class RefundServiceTest {

    private RefundService refundService;
    private Ticket validTicket;

    @BeforeEach
    void setUp() {
        CinemaSystem.soldTickets.clear();
        CinemaSystem.mockCardDB.clear();

        CinemaSystem.mockCardDB.put("1111222233334444", 1000.0);

        refundService = new RefundService();

        Movie movie = new Movie2D("Refund Movie", 120, 100.0, Genre.DRAMA, AgeRating.PLUS_13);
        CinemaHall hall = new StandardHall("Hall 1", 5, 5);
        ShowTime showTime = new ShowTime(LocalDateTime.now().plusDays(1), movie, hall);
        Seat seat = showTime.getSeat(1, 1);
        Customer customer = new Customer("Test", "User", "test@mail.com", "5555555555", 2000);

        validTicket = new Ticket("PNR123", customer, showTime, seat, 100.0, 100.0);

        CinemaSystem.soldTickets.add(validTicket);
    }

    @Test
    void testProcessRefund_Successful() {

        String pnr = validTicket.getPnrCode();

        assertDoesNotThrow(() -> {
            refundService.processRefund(pnr);
        });

        boolean ticketExists = CinemaSystem.soldTickets.stream()
                .anyMatch(t -> t.getPnrCode().equals(pnr));

        assertFalse(ticketExists, "Ticket should be removed from system after refund");

        assertEquals(SeatStatus.AVAILABLE, validTicket.getSeat().getStatus(), "Seat should be AVAILABLE after refund");
    }

    @Test
    void testProcessRefund_InvalidPNR() {
        String fakePnr = "PNR-99999999";

        assertThrows(InvalidPNRException.class, () -> {
            refundService.processRefund(fakePnr);
        });
    }
}