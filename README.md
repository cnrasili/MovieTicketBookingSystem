# Movie Ticket Booking System

A data-driven, console-based movie ticket booking application developed using Java. This project serves as the final assignment for the Object-Oriented Programming (OOP) course. It demonstrates advanced OOP concepts including **Polymorphism**, **Interface Segregation**, **Dependency Injection**, and **Exception Handling**.

Unlike static simulations, this project uses **CSV files** to manage system data, making it dynamic and configurable without code changes.

## Features

* **Data-Driven Architecture:**
    * **CSV Integration:** Movies, Branches, Credit Cards, and Student IDs are loaded dynamically from external CSV files (`movies.csv`, `branches.csv`, etc.) at startup.
    * **Dynamic Scheduling:** Showtimes are automatically generated for the next 5 days based on the loaded movie data.
* **Booking Workflow:** Sequential selection of cinema branch, movie, showtime, and seats.
* **Seat Selection:** Visual representation of seat availability (Standard, LoveSeat, VIP) with real-time status updates.
* **Dynamic Pricing Strategy:**
    * **Standard Strategy:** Base price calculation based on movie type (2D/3D) and Hall type (IMAX/VIP).
    * **Student Strategy:** Discount for validated student IDs (verified against `students.csv`).
    * **First Session Strategy:** Automatic discount for the first show of the day.
* **Refund Mechanism:** Ticket cancellation using PNR codes within allowed timeframes.
* **Validation:** Regex-based verification for credit cards, phone numbers, and emails.

## Technical Structure

* **Architecture:**
    * `model`: Data entities (`Movie`, `Ticket`, `Seat`) and interfaces (`Bookable`).
    * `service`: Business logic (`Booking`, `RefundService`, `DataInitializer`, `CinemaSystem`).
    * `util`: Console input/output handling (`ConsoleHelper`).
    * `exception`: Custom exception classes (`PaymentFailedException`, `SeatOccupiedException`, etc.).
* **Key OOP Concepts:**
    * **Interface:** `Bookable` interface implemented by `Seat` class; `PaymentService` and `PriceStrategy` interfaces.
    * **Abstraction:** `Movie` (2D/3D) and `CinemaHall` (IMAX/Standard) hierarchies.
    * **Singleton/Static Datastore:** `CinemaSystem` acts as an in-memory database populated by CSVs.

## Installation and Execution

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/cnrasili/movie-booking-system.git](https://github.com/cnrasili/movie-booking-system.git)
    ```
2.  **Open the project:** Open the folder in an IDE like IntelliJ IDEA or Eclipse.
3.  **Verify CSV Files:** Ensure the following files exist in the project root directory:
    * `movies.csv`
    * `branches.csv`
    * `credit_cards.csv`
    * `students.csv`
4.  **Run:** Navigate to `src/com/cnrasili/moviebooking/Main.java` and run the `main` method.

## Usage Guide

### 1. Booking a Ticket
Select option `1` from the main menu.
1.  **Branch Selection:** Choose a location loaded from `branches.csv`.
2.  **Movie Selection:** Choose a movie loaded from `movies.csv`.
3.  **Showtime:** Select a session (dynamically generated for the next 5 days).
4.  **Seat:** Enter Row and Column numbers (e.g., 3-4) based on the seat map.
5.  **Customer Info:** Input name, email, and birth year.
6.  **Discount:** Enter a valid Student ID (checked against `students.csv`).
7.  **Payment:** Enter a 16-digit credit card number (checked against `credit_cards.csv`).

### 2. Refund
Select option `2` from the main menu.
* Enter the PNR code provided upon successful booking.
* The system verifies the ticket exists in memory and cancels it if the showtime has not passed.

## Configuration & Test Data

The application loads initial data from CSV files located in the root directory. You can modify these files to test different scenarios.

### **1. Credit Cards (`credit_cards.csv`)**
Format: `CardNumber,Balance`
* `1111111111111111` : High balance (Successful payment)
* `2222222222222222` : Medium balance
* `3333333333333333` : Low balance (Triggers **PaymentFailedException**)

### **2. Student IDs (`students.csv`)**
Format: `StudentID`
* `ST1001`, `ST1002`, `ST1003` : Valid IDs (Triggers **Student Discount**)

### **3. Movies (`movies.csv`)**
Format: `Name, Duration, Price, Genre, Rating, Type`
* Example: `Avatar 2, 192, 100.0, SCI_FI, PLUS_13, 3D`

### **4. Branches (`branches.csv`)**
Format: `Name, City, District`
* Example: `Paribu Cineverse Marmarapark, İstanbul, Esenyurt`

## Documentation and Project Management

* **UML Diagrams:** Updated Class diagrams reflecting the final architecture are located in the `/docs` directory.
* **Project Tracking:** Managed via [GitHub Projects Kanban Board](https://github.com/users/cnrasili/projects/1/views/1).