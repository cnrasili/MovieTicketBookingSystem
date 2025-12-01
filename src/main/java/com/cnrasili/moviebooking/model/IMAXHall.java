package com.cnrasili.moviebooking.model;

public class IMAXHall extends CinemaHall {

    public IMAXHall(String name, int totalRows, int totalCols) {
        super(name, totalRows, totalCols);
    }

    @Override
    public double getPriceMultiplier() {
        return 1.5;
    }
}
