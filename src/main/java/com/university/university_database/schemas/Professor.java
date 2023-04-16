package com.university.university_database.schemas;

public class Professor extends Person {

    public Professor(String ID, String password, String firstName, String lastName, String address, String phone, String email, int departmentID) {
        super(ID, password, departmentID, firstName, lastName, address, phone, email);
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
