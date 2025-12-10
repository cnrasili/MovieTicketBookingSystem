package com.cnrasili.moviebooking.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific branch of the cinema chain located in a particular city and district.
 * <p>
 * A branch acts as a container for multiple {@link CinemaHall}s.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class CinemaBranch {
    private String name;
    private String city;
    private String district;
    private List<CinemaHall> halls;

    /**
     * Constructs a new CinemaBranch.
     *
     * @param name     The commercial name of the branch (e.g., "Paribu Cineverse Marmarapark").
     * @param city     The city where the branch is located.
     * @param district The district where the branch is located.
     */
    public CinemaBranch(String name, String city, String district) {
        this.name = name;
        this.city = city;
        this.district = district;
        this.halls = new ArrayList<>();
    }

    /**
     * Adds a new cinema hall to this branch.
     *
     * @param hall The {@link CinemaHall} object to be added.
     */
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

    /**
     * Retrieves the list of halls available in this branch.
     *
     * @return A list of {@link CinemaHall} objects.
     */
    public List<CinemaHall> getHalls() {
        return halls;
    }

    /**
     * Returns a string representation of the branch location.
     * Format: "Name - District/City"
     *
     * @return Formatted location string.
     */
    @Override
    public String toString() {
        return name + " - " + district + "/" + city;
    }
}
