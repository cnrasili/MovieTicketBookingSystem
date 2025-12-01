package com.cnrasili.moviebooking.model;

public class VIPHall extends CinemaHall {

    public VIPHall(String name, int totalRows, int totalCols) {
        super(name, totalRows, totalCols);
    }

    @Override
    public double getPriceMultiplier() {
        return 2.0;
    }
}
