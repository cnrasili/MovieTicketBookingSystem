package com.cnrasili.moviebooking.service;
import com.cnrasili.moviebooking.exception.AgeLimitException;
import com.cnrasili.moviebooking.exception.PaymentFailedException;
import com.cnrasili.moviebooking.exception.SeatOccupiedException;
import com.cnrasili.moviebooking.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class BookingManagerTest {

    private BookingManager bookingManager;
    private PaymentService paymentService;
    private ShowTime showTime;
    private Seat seat;
    private Movie movie;
    private Customer customer;

    @BeforeEach
    void setUp() {
        bookingManager = new BookingManager();
        paymentService = new CreditCardPaymentService();
        movie = new Movie2D("Test Movie", 120, 100.0, Genre.ACTION, AgeRating.PLUS_13);
        CinemaHall hall = new StandardHall("Test Hall", 5, 5);
        showTime = new ShowTime(LocalDateTime.now().plusDays(1).withHour(14), movie, hall);
        seat = showTime.getSeat(1, 1);
        customer = new Customer("Test", "User", "test@mail.com", "5555555555", 2000);

        CinemaSystem.activeShowTimes.clear();
        CinemaSystem.activeShowTimes.add(showTime);
    }

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

    @Test
    void testCreateTicket_StudentDiscount() {
        String richCard = "1111111111111111";
        PriceStrategy studentStrategy = new StudentStrategy();

        assertDoesNotThrow(() -> {
            Ticket ticket = bookingManager.createTicket(customer, showTime, seat, studentStrategy, paymentService, richCard);

            assertEquals(70.0, ticket.getFinalPrice(), "Student discount (20%) should be applied correctly");
        });
    }

    @Test
    void testCreateTicket_LoveSeatPricing() {
        Seat loveSeat = new LoveSeat(1, 2);

        String richCard = "1111111111111111";
        PriceStrategy strategy = new StandardPriceStrategy();

        assertDoesNotThrow(() -> {
            Ticket ticket = bookingManager.createTicket(customer, showTime, loveSeat, strategy, paymentService, richCard);

            assertEquals(180.0, ticket.getFinalPrice(), "LoveSeat price (x2) should be calculated correctly");
            assertEquals(SeatStatus.BOOKED, loveSeat.getStatus(), "LoveSeat should be booked");
        });
    }

    @Test
    void testCreateTicket_SeatOccupiedException() {
        seat.reserve();

        String richCard = "1111111111111111";
        PriceStrategy strategy = new StandardPriceStrategy();

        assertThrows(SeatOccupiedException.class, () -> {
            bookingManager.createTicket(customer, showTime, seat, strategy, paymentService, richCard);
        });
    }

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

    @Test
    void testCreateTicket_PaymentFailedException_InsufficientFunds() {
        String poorCard = "3333333333333333";
        PriceStrategy strategy = new StandardPriceStrategy();

        assertThrows(PaymentFailedException.class, () -> {
            bookingManager.createTicket(customer, showTime, seat, strategy, paymentService, poorCard);
        });
    }

    @Test
    void testCreateTicket_PaymentFailedException_InvalidCard() {
        String unknownCard = "9999999999999999";

        assertThrows(PaymentFailedException.class, () -> {
            bookingManager.createTicket(customer, showTime, seat, new StandardPriceStrategy(), paymentService, unknownCard);
        });
    }
}