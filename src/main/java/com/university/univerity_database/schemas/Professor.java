package com.university.univerity_database.schemas;

import java.time.LocalDate;
import java.util.Arrays;

public class Professor extends Person {

    private final int departmentID;

    public Professor(String ID, String password, String firstName, String lastName, String address, String phone, String email, int departmentID) {
        super(ID, password, firstName, lastName, address, phone, email);

        if(!validateDepartmentID(departmentID)) {
            throw new IllegalArgumentException("The department ID is not a valid value! Please select correctly");
        }

        this.departmentID = departmentID;
    }

    private boolean validateDepartmentID(int departmentID) {
        return Arrays.stream(Department.values()).anyMatch(d -> d.getDepartmentID() == departmentID); // departmentID has to be valid in Department Enum
    }

    public int getDepartmentID() {
        return departmentID;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "departmentID=" + departmentID +
                ", ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
