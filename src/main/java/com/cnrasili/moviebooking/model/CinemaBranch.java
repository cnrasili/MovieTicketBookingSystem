package com.cnrasili.moviebooking.model;

import java.util.ArrayList;
import java.util.List;

public class CinemaBranch {
    private String name;
    private String city;
    private String district;
    private List<CinemaHall> halls;

    public CinemaBranch(String name, String city, String district) {
        this.name = name;
        this.city = city;
        this.district = district;
        this.halls = new ArrayList<>();
    }

    public void addHall(CinemaHall hall) {
        this.halls.add(hall);
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public List<CinemaHall> getHalls() {
        return halls;
    }

    @Override
    public String toString() {
        return name + " - " + district + "/" + city;
    }
}
