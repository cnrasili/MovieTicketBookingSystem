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
        for (int row = 1; row <= hall.getTotalRows(); row++) {
            for (int col = 1; col <= hall.getTotalCols(); col++) {
                seats.add(new StandardSeat(row, col));
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

    public Movie getMovie() { return movie; }
    public CinemaHall getHall() { return hall; }
    public List<Seat> getSeats() { return seats; }
    public LocalDateTime getTime() { return time; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return movie.getTitle() + " (" + movie.getGenre() + ") - " + time.format(formatter) + " @ " + hall.getName();
    }
}
