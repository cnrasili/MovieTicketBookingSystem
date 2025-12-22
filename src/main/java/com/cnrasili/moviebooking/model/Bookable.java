package com.cnrasili.moviebooking.model;

public interface Bookable {
    void reserve();
    void cancelBooking();
    boolean isAvailable();
}
