package com.cnrasili.moviebooking.service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service responsible for validating student credentials.
 * <p>
 * This class simulates an external verification system (e.g., University Database)
 * to prevent unauthorized use of the Student Discount.
 * </p>
 *
 * @author cnrasili
 * @version 1.0
 */
public class StudentService {

    private static final Set<String> validStudentIds = new HashSet<>();

    static {
        validStudentIds.add("ST1001");
        validStudentIds.add("ST1002");
        validStudentIds.add("ST1003");
    }

    /**
     * Checks if the provided student ID is valid and active.
     *
     * @param studentId The unique student identifier string.
     * @return {@code true} if the ID exists in the approved list; {@code false} otherwise.
     */
    public boolean validateStudentId(String studentId) {
        return studentId != null && validStudentIds.contains(studentId);
    }
}
