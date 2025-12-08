package com.cnrasili.moviebooking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShowTime {
    private LocalDateTime time;
    private Movie movie;
    private CinemaHall hall;
    private List<Seat> seats;

    public ShowTime(LocalDateTime time, Movie movie, CinemaHall hall) {
        this.time = time;
        this.movie = movie;
        this.hall = hall;
        this.seats = new ArrayList<>();
        initializeSeats();
    }

    private void initializeSeats() {
        for (Seat originalSeat : hall.getSeats()) {

            if (originalSeat instanceof LoveSeat) {
                this.seats.add(new LoveSeat(originalSeat.getRow(), originalSeat.getNumber()));
            }
            else {
                this.seats.add(new StandardSeat(originalSeat.getRow(), originalSeat.getNumber()));
            }
        }
    }

    public Seat getSeat(int row, int number) {
        for (Seat seat : seats) {
            if (seat.getRow() == row && seat.getNumber() == number) {
                return seat;
            }
        }
        return null;
    }

    public double getStandardPrice() {
        return movie.calculatePrice() * hall.getPriceMultiplier();
    }

    public Movie getMovie() { return movie; }
    public CinemaHall getHall() { return hall; }
    public List<Seat> getSeats() { return seats; }
    public LocalDateTime getTime() { return time; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return time.format(formatter) + " @ " + hall.getName() + " (Price: " + getStandardPrice() + " TL)";
    }
}
