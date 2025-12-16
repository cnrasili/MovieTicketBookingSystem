package com.cnrasili.moviebooking.service;

import com.cnrasili.moviebooking.exception.AgeLimitException;
import com.cnrasili.moviebooking.exception.PaymentFailedException;
import com.cnrasili.moviebooking.exception.SeatOccupiedException;
import com.cnrasili.moviebooking.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BookingManager} class.
 * <p>
 * This test suite validates the core business logic of the ticket booking process, covering:
 * <ul>
 * <li>Successful booking scenarios (Standard, Student, LoveSeat).</li>
 * <li>Dynamic price calculations and discounts.</li>
 * <li>Exception handling (Occupied seats, Age limits, Payment failures).</li>
 * </ul>
 * uses JUnit 5 for assertions.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
class BookingManagerTest {

    private BookingManager bookingManager;
    private PaymentService paymentService;
    private ShowTime showTime;
    private Seat seat;
    private Movie movie;
    private Customer customer;

    /**
     * Sets up the test environment before each test method execution.
     * <p>
     * Initializes a fresh instance of {@link BookingManager}, mock payment services,
     * and sample data (Movie, Hall, ShowTime, Customer).
     * It also clears the static {@link CinemaSystem} state to ensure test isolation.
     * </p>
     */
    @BeforeEach
    void setUp() {
        bookingManager = new BookingManager();
        paymentService = new CreditCardPaymentService();
        movie = new Movie2D("Test Movie", 120, 100.0, Genre.ACTION, AgeRating.PLUS_13);
        CinemaHall hall = new StandardHall("Test Hall", 5, 5);
        showTime = new ShowTime(LocalDateTime.now().plusDays(1).withHour(14), movie, hall);
        seat = showTime.getSeat(1, 1);
        customer = new Customer("Test", "User", "test@mail.com", "5555555555", 2000);

        // Reset static database for isolation
        CinemaSystem.activeShowTimes.clear();
        CinemaSystem.activeShowTimes.add(showTime);
    }

    /**
     * Verifies that a ticket is successfully created when all conditions are met.
     * <p>
     * Checks if:
     * <ul>
     * <li>The returned ticket object is not null.</li>
     * <li>The seat status is updated to {@code BOOKED}.</li>
     * <li>The standard price calculation is correct.</li>
     * </ul>
     * </p>
     */
    @Test
    void testCreateTicket_SuccessfulBooking() {
        String richCard = "1111111111111111";
        PriceStrategy strategy = new StandardPriceStrategy();

        assertDoesNotThrow(() -> {
            Ticket ticket = bookingManager.createTicket(customer, showTime, seat, strategy, paymentService, richCard);

            assertNotNull(ticket, "Ticket should be created (should not be null)");
            assertEquals(SeatStatus.BOOKED, seat.getStatus(), "Seat status should be BOOKED");
            assertEquals("Test Movie", ticket.getShowTime().getMovie().getTitle(), "Movie title should be correct");

            assertEquals(90.0, ticket.getFinalPrice(), "Standard price calculation should be correct");
        });
    }

    /**
     * Verifies that the "First Session of the Day" discount is applied automatically.
     * <p>
     * Creates an early morning showtime (09:00) and expects a price reduction.
     * </p>
     */
    @Test
    void testCreateTicket_FirstSessionDiscount() {
        ShowTime earlyShow = new ShowTime(LocalDateTime.now().plusDays(1).withHour(9), movie, new StandardHall("Early Hall", 5, 5));
        Seat earlySeat = earlyShow.getSeat(1, 1);

        CinemaSystem.activeShowTimes.clear();
        CinemaSystem.activeShowTimes.add(earlyShow);

        String richCard = "1111111111111111";
        PriceStrategy strategy = new StandardPriceStrategy();

        assertDoesNotThrow(() -> {
            Ticket ticket = bookingManager.createTicket(customer, earlyShow, earlySeat, strategy, paymentService, richCard);

            assertEquals(90.0, ticket.getFinalPrice(), "First session discount (10%) should be applied correctly");
        });
    }

    /**
     * Verifies that the {@link StudentStrategy} applies the correct discount percentage.
     */
    @Test
    void testCreateTicket_StudentDiscount() {
        String richCard = "1111111111111111";
        PriceStrategy studentStrategy = new StudentStrategy();

        assertDoesNotThrow(() -> {
            Ticket ticket = bookingManager.createTicket(customer, showTime, seat, studentStrategy, paymentService, richCard);

            // Expects 20% discount
            assertEquals(70.0, ticket.getFinalPrice(), "Student discount (20%) should be applied correctly");
        });
    }

    /**
     * Verifies that selecting a {@link LoveSeat} correctly applies the price multiplier (x2).
     */
    @Test
    void testCreateTicket_LoveSeatPricing() {
        Seat loveSeat = new LoveSeat(1, 2);

        String richCard = "1111111111111111";
        PriceStrategy strategy = new StandardPriceStrategy();

        assertDoesNotThrow(() -> {
            Ticket ticket = bookingManager.createTicket(customer, showTime, loveSeat, strategy, paymentService, richCard);

            // Base * 2 (LoveSeat)
            assertEquals(180.0, ticket.getFinalPrice(), "LoveSeat price (x2) should be calculated correctly");
            assertEquals(SeatStatus.BOOKED, loveSeat.getStatus(), "LoveSeat should be booked");
        });
    }

    /**
     * Verifies that {@link SeatOccupiedException} is thrown when attempting to book an already reserved seat.
     */
    @Test
    void testCreateTicket_SeatOccupiedException() {
        seat.reserve(); // Occupy the seat first

        String richCard = "1111111111111111";
        PriceStrategy strategy = new StandardPriceStrategy();

        assertThrows(SeatOccupiedException.class, () -> {
            bookingManager.createTicket(customer, showTime, seat, strategy, paymentService, richCard);
        });
    }

    /**
     * Verifies that {@link AgeLimitException} is thrown when an underage customer attempts to book a restricted movie.
     */
    @Test
    void testCreateTicket_AgeLimitException() {
        Movie adultMovie = new Movie2D("Adult Movie", 90, 100.0, Genre.HORROR, AgeRating.PLUS_18);
        ShowTime adultShow = new ShowTime(LocalDateTime.now().plusDays(1), adultMovie, new StandardHall("H", 5, 5));
        Seat testSeat = adultShow.getSeat(1, 1);

        Customer kid = new Customer("Kid", "Boy", "kid@mail.com", "555", 2015);
        String richCard = "1111111111111111";

        assertThrows(AgeLimitException.class, () -> {
            bookingManager.createTicket(kid, adultShow, testSeat, new StandardPriceStrategy(), paymentService, richCard);
        });
    }

    /**
     * Verifies that {@link PaymentFailedException} is thrown when the card has insufficient funds.
     */
    @Test
    void testCreateTicket_PaymentFailedException_InsufficientFunds() {
        String poorCard = "3333333333333333";
        PriceStrategy strategy = new StandardPriceStrategy();

        assertThrows(PaymentFailedException.class, () -> {
            bookingManager.createTicket(customer, showTime, seat, strategy, paymentService, poorCard);
        });
    }

    /**
     * Verifies that {@link PaymentFailedException} is thrown when an invalid/unknown card number is provided.
     */
    @Test
    void testCreateTicket_PaymentFailedException_InvalidCard() {
        String unknownCard = "9999999999999999";

        assertThrows(PaymentFailedException.class, () -> {
            bookingManager.createTicket(customer, showTime, seat, new StandardPriceStrategy(), paymentService, unknownCard);
        });
    }
}