package com.cnrasili.moviebooking.model;
import java.util.ArrayList;
import java.util.List;

public abstract class CinemaHall {
    private String name;
    private int totalRows;
    private int totalCols;
    private List<Seat> seats;

    public CinemaHall(String name, int totalRows, int totalCols) {
        this.name = name;
        this.totalRows = totalRows;
        this.totalCols = totalCols;
        this.seats = new ArrayList<>();
        initSeats();
    }

    protected void initSeats() {
        for (int row = 1; row <= totalRows; row++) {
            for (int col = 1; col <= totalCols; col++) {
                seats.add(new StandardSeat(row, col));
            }
        }
    }

    public abstract double getPriceMultiplier();

    public String getName() {
        return name;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return name + " (" + totalRows + "x" + totalCols + ")";
    }
}
