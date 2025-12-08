package com.cnrasili.moviebooking.service;

import java.util.HashSet;
import java.util.Set;

public class StudentService {

    private static final Set<String> validStudentIds = new HashSet<>();

    static {
        validStudentIds.add("ST1001");
        validStudentIds.add("ST1002");
        validStudentIds.add("ST1003");
    }

    public boolean validateStudentId(String studentId) {
        return studentId != null && validStudentIds.contains(studentId);
    }
}
