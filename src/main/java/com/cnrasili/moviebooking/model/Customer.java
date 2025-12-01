package com.cnrasili.moviebooking.model;

public class Customer {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private int birthYear;

    public Customer(String name, String surname, String email, String phoneNumber, int birthYear) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthYear = birthYear;
    }

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