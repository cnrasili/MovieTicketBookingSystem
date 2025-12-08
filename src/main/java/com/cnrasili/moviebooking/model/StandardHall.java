package com.cnrasili.moviebooking.model;

public class StandardHall extends CinemaHall {

    public StandardHall(String name, int totalRows, int totalCols) {
        super(name, totalRows, totalCols);
    }

    @Override
    public double getPriceMultiplier() {
        return 1.0;
    }

    @Override
    protected void initSeats() {
        getSeats().clear();

        for (int row = 1; row <= getTotalRows(); row++) {
            for (int col = 1; col <= getTotalCols(); col++) {
                if (row == getTotalRows()) {
                    getSeats().add(new LoveSeat(row, col));
                } else {
                    getSeats().add(new StandardSeat(row, col));
                }
            }
        }
    }
}
