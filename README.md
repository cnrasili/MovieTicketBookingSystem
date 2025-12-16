# Movie Ticket Booking System

A console-based movie ticket booking application developed using Java. This project serves as the final assignment for the Object-Oriented Programming (OOP) course. It demonstrates class hierarchy, interface implementation, exception handling, and unit testing.

## Features

* **Booking Workflow:** Sequential selection of cinema branch, movie, showtime, and seats.
* **Seat Selection:** Visual representation of seat availability (Standard and LoveSeat types).
* **Dynamic Pricing:**
    * **Standard Strategy:** Base price calculation.
    * **Student Strategy:** 20% discount for validated student IDs.
    * **First Session Strategy:** 10% discount for the first show of the day.
* **Refund Mechanism:** Ticket cancellation using PNR codes within allowed timeframes.
* **Validation:** Input verification for credit cards, phone numbers, and emails.

## Technical Structure

* **Architecture:**
    * `model`: Data classes (Movie, Ticket, Seat, etc.)
    * `service`: Business logic (BookingManager, RefundService)
    * `util`: Console input/output handling
    * `exception`: Custom exception classes

## Installation and Execution

1.  Clone the repository:
    ```bash
    git clone [https://github.com/cnrasili/movie-booking-system.git](https://github.com/cnrasili/movie-booking-system.git)
    ```
2.  Open the project in an IDE (IntelliJ IDEA or Eclipse).
3.  Navigate to `src/com/cnrasili/moviebooking/Main.java`.
4.  Run the `main` method to start the console application.

## Usage Guide

### 1. Booking a Ticket
Select option `1` from the main menu. Follow the prompts:
1.  **Branch Selection:** Choose a cinema location.
2.  **Movie Selection:** Choose a movie from the list.
3.  **Showtime:** Select an available session.
4.  **Seat:** Enter Row and Column numbers based on the displayed map.
5.  **Customer Info:** Input name, email, and birth year.
6.  **Discount:** Enter a valid Student ID if applicable.
7.  **Payment:** Enter a 16-digit credit card number.

### 2. Refund
Select option `2` from the main menu.
* Enter the PNR code provided upon successful booking.
* The system checks if the showtime has passed before processing the refund.

## Test Data

The application uses mock data for simulation. Use the following values to test different scenarios:

**Credit Cards:**
* `1111111111111111` : High balance (Successful payment)
* `2222222222222222` : Medium balance
* `3333333333333333` : Low balance (Triggers PaymentFailedException)

**Student IDs:**
* `ST1001` : Valid ID (Applies discount)
* `ST1002` : Valid ID
* `ST1003` : Valid ID

## Documentation and Project Management

* **UML Diagrams:** Class diagrams are located in the `/docs` directory.
* **Project Tracking:** Managed via [GitHub Projects Kanban Board](https://github.com/users/cnrasili/projects/1/views/1).