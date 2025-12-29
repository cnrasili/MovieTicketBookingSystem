package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.InvalidPNRException;
import com.cnrasili.moviebooking.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RefundService} class.
 * <p>
 * This test suite validates the ticket cancellation and refund logic.
 * It ensures that the system correctly removes tickets, frees up seats,
 * and handles invalid PNR codes appropriately.
 * <br>
 * <b>Note:</b> Similar to {@code BookingTest}, this class uses manual data injection
 * into {@link CinemaSystem} to avoid dependencies on external CSV files during testing.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
class RefundServiceTest {

    private RefundService refundService;
    private Ticket validTicket;

    /**
     * Sets up the test environment before each test execution.
     * <p>
     * Steps performed:
     * <ol>
     * <li>Clears global storage ({@code soldTickets}, {@code mockCardDB}) to ensure a clean state.</li>
     * <li>Injects mock balance for the credit card used in the test.</li>
     * <li>Initializes a sample Movie, Hall, ShowTime, and Customer.</li>
     * <li>Creates a valid {@link Ticket} with a known PNR ("PNR123") and adds it to the system.</li>
     * </ol>
     * </p>
     */
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

    /**
     * Verifies that a valid ticket is successfully refunded.
     * <p>
     * This test checks:
     * <ul>
     * <li>The method executes without throwing exceptions.</li>
     * <li>The ticket is removed from the {@code CinemaSystem.soldTickets} list.</li>
     * <li>The associated {@link Seat} status is reverted to {@code AVAILABLE}.</li>
     * </ul>
     * </p>
     */
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

    /**
     * Verifies that {@link InvalidPNRException} is thrown when attempting to refund a non-existent PNR.
     */
    @Test
    void testProcessRefund_InvalidPNR() {
        String fakePnr = "PNR-99999999";

        assertThrows(InvalidPNRException.class, () -> {
            refundService.processRefund(fakePnr);
        });
    }
}