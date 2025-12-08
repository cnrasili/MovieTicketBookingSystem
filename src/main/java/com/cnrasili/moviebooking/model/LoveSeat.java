package com.cnrasili.moviebooking.model;

public class LoveSeat extends Seat {

    public LoveSeat(int row, int number) {
        super(row, number);
    }

    @Override
    public double getPriceMultiplier() {
        return 2.0;
    }

    @Override
    public String toString() {
        return isAvailable() ? "[L" + getRow() + "-" + getNumber() + "]" : "[ X X ]";
    }
}
