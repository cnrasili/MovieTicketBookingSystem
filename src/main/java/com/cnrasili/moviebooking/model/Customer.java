package com.cnrasili.moviebooking.model;

/**
 * Represents a customer (user) in the booking system.
 * Holds personal contact details used for ticketing and notifications.
 *
 * @author cnrasili
 * @version 1.0
 */
public class Customer {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private int birthYear;

    /**
     * Constructs a new Customer.
     *
     * @param name        First name.
     * @param surname     Last name.
     * @param email       Email address.
     * @param phoneNumber Contact number.
     * @param birthYear   Year of birth (used for age validation logic in BookingManager).
     */
    public Customer(String name, String surname, String email, String phoneNumber, int birthYear) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthYear = birthYear;
    }

    /**
     * Returns the full name of the customer.
     *
     * @return A string combining name and surname (e.g., "John Doe").
     */
    public String getFullName() {
        return name + " " + surname;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}