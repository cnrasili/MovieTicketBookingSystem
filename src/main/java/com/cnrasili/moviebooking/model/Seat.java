package com.cnrasili.moviebooking.model;

public abstract class Seat {
    private int row;
    private int number;
    private SeatStatus status;

    public Seat(int row, int number) {
        this.row = row;
        this.number = number;
        this.status = SeatStatus.AVAILABLE;
    }

    public abstract double getPriceMultiplier();

    public void reserve() {
        this.status = SeatStatus.BOOKED;
    }

    public void cancelBooking() {
        this.status = SeatStatus.AVAILABLE;
    }

    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public SeatStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "R" + row + "-N" + number;
    }
}
