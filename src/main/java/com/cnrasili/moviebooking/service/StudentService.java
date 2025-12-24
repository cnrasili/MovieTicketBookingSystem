package com.cnrasili.moviebooking.service;

/**
 * Service responsible for validating student credentials for discount eligibility.
 * <p>
 * This class acts as a validator interface for the student discount system.
 * Instead of maintaining an internal list, it verifies IDs against the central registry
 * located in {@link CinemaSystem#validStudentIds}.
 * <br>
 * <b>Note:</b> The valid IDs are loaded from the external {@code students.csv} file
 * by the {@link DataInitializer} at application startup.
 * </p>
 *
 * @author cnrasili
 * @version 1.1 (Refactored to use central CSV-loaded data)
 */
public class StudentService {

    /**
     * Checks if the provided student ID is valid and active.
     * <p>
     * It queries the {@link CinemaSystem} to see if the ID exists in the currently loaded list.
     * </p>
     *
     * @param studentId The unique student identifier string.
     * @return {@code true} if the ID is found in the central system; {@code false} otherwise.
     */
    public boolean validateStudentId(String studentId) {
        return studentId != null && CinemaSystem.validStudentIds.contains(studentId);
    }
}
