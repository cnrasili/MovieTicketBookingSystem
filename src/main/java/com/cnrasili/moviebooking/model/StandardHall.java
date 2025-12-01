package com.cnrasili.moviebooking.model;

public class StandardHall extends CinemaHall {

    public StandardHall(String name, int totalRows, int totalCols) {
        super(name, totalRows, totalCols);
    }

    @Override
    public double getPriceMultiplier() {
        return 1.0;
    }
}
