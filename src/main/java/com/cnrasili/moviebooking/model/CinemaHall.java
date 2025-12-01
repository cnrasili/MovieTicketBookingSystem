package com.cnrasili.moviebooking.model;

public abstract class CinemaHall {
    private String name;
    private int totalRows;
    private int totalCols;

    public CinemaHall(String name, int totalRows, int totalCols) {
        this.name = name;
        this.totalRows = totalRows;
        this.totalCols = totalCols;
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

    @Override
    public String toString() {
        return name + " (" + totalRows + "x" + totalCols + ")";
    }
}
