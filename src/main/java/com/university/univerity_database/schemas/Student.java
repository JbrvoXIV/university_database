package com.university.univerity_database.schemas;

import java.time.LocalDate;

public class Student extends Person {

    public Student(String ID, String firstName, String lastName, String address, String phone, String email, LocalDate dob) {
        super(ID, firstName, lastName, address, phone, email, dob);
    }
}
