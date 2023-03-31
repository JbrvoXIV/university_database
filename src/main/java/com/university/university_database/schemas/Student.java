package com.university.university_database.schemas;

import java.time.LocalDate;

public class Student extends Person {

    private LocalDate dob;

    public Student(String ID, String password, String firstName, String lastName, String address, String phone, String email, LocalDate dob) {
        super(ID, password, firstName, lastName, address, phone, email);

        if(!validateDOB(dob)) {
            throw new IllegalArgumentException("The date of birth, " + dob + ", is too old! Please try again.");
        }

        this.dob = dob;
    }

    private boolean validateDOB(LocalDate dob) {
        int age = LocalDate.now().getYear() - dob.getYear();
        boolean isOldEnough = age >= 18 && age <= 60 ; // young if less than 60
        return isOldEnough;
    }

    public LocalDate getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                '}';
    }
}
