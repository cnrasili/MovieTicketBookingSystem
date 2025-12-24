package com.cnrasili.moviebooking.model;

/**
 * Interface defining the contract for any entity that can be reserved or booked.
 * <p>
 * This interface abstracts the booking behavior, allowing different types of objects
 * (such as Seats, Hotel Rooms, or Event Spots) to be managed uniformly.
 * <br>
 * Any class implementing this interface must provide specific logic for:
 * <ul>
 * <li>Reserving the item (changing status to BOOKED/OCCUPIED).</li>
 * <li>Canceling the reservation (reverting status to AVAILABLE).</li>
 * <li>Checking current availability status.</li>
 * </ul>
 * Currently implemented by {@link com.cnrasili.moviebooking.model.Seat}.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public interface Bookable {

    /**
     * Marks the item as reserved.
     * <p>
     * Implementation should handle status transitions (e.g., from AVAILABLE to BOOKED)
     * and throw exceptions if the item is already occupied.
     * </p>
     */
    void reserve();

    /**
     * Cancels an existing reservation for this item.
     * <p>
     * Implementation should revert the status of the item to AVAILABLE,
     * allowing it to be booked again.
     * </p>
     */
    void cancelBooking();

    /**
     * Checks if the item is currently available for booking.
     *
     * @return {@code true} if the item is free/available; {@code false} otherwise.
     */
    boolean isAvailable();
}
