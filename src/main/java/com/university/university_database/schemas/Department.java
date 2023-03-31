package com.university.university_database.schemas;

public enum Department {
    COMPUTER_SCIENCE(1),
    INFORMATION_TECHNOLOGY(2),
    CYBER_SECURITY(3),
    COMPUTER_ENGINEERING(4),
    MECHANICAL_ENGINEERING(5);

    private final int departmentID;

    Department(int departmentID) {
        this.departmentID = departmentID;
    }

    public int getDepartmentID() {
        return departmentID;
    }
}
