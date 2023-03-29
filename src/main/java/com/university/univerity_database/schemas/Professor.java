package com.university.univerity_database.schemas;

import java.time.LocalDate;

public class Professor extends Person {

    private final int departmentID;

    public Professor(String ID, String firstName, String lastName, String address, String phone, String email, LocalDate dob, int departmentID) {
        super(ID, firstName, lastName, address, phone, email, dob);

        if(!validateDepartmentID(departmentID)) {
            throw new IllegalArgumentException("The department ID is not a valid value! Please select correctly");
        }

        this.departmentID = departmentID;
    }

    private boolean validateDepartmentID(int departmentID) {
        return departmentID >= 1 && departmentID <= 5; // department is either department_id 1 - 5 in db
    }

    public int getDepartmentID() {
        return departmentID;
    }
}
