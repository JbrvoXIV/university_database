package com.university.univerity_database.schemas;

import java.time.LocalDate;

public class Person {

    protected int ID;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phone;
    protected String email;
    protected LocalDate dob;

    public Person(String ID, String firstName, String lastName, String address, String phone, String email, LocalDate dob) {
        IllegalArgumentException e = validateInputs(ID, firstName, lastName, address, phone, email, dob);

        if(e != null) {
            throw e; // error was found in inputs
        }

        this.ID = Integer.parseInt(ID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
    }

    private IllegalArgumentException validateInputs(String ID, String firstName, String lastName, String address, String phone, String email, LocalDate dob) {
        if(!validateID(ID)) {
            return new IllegalArgumentException("The ID value you entered, " + ID + ", is incorrect! Please try again.");
        }
        if(!firstName.matches("[a-zA-Z]+")) {
            return new IllegalArgumentException("The first name, " + firstName + ", contains non-alphabetic values! Please try again.");
        }
        if(!lastName.matches("[a-zA-Z]+")) {
            return new IllegalArgumentException("The last name, " + lastName + ", contains non-alphabetic values! Please try again.");
        }
        if(!validateAddress(address)) {
            return new IllegalArgumentException("The street address, " + address + ", is not in a valid format! Please try again.");
        }
        if(!validatePhone(phone)) {
            return new IllegalArgumentException("The phone number, " + phone + ", is not in a valid format! Please try again.");
        }
        if(!validateEmail(email)) {
            return new IllegalArgumentException("The email, " + email + ", is not in a valid format! Please try again.");
        }
        if(!validateDOB(dob)) {
            return new IllegalArgumentException("The date of birth, " + dob + ", is too old! Please try again.");
        }
        return null; // no errors found
    }

    private boolean validateID(String id) {
        int idParsed;
        try {
            idParsed = Integer.parseInt(id);
        } catch(Exception e) {
            e.printStackTrace();
            idParsed = -1;
        }
        return idParsed >= 100000 && idParsed <= 999999;
    }

    private boolean validateAddress(String address) {
        boolean addressIsNotEmpty = !address.isEmpty();
        return addressIsNotEmpty;
    }

    private boolean validatePhone(String phone) {
        String phoneValidationRegex = "\\d{10}"; // 1234567890 -> true
        return phone.matches(phoneValidationRegex);
    }

    private boolean validateEmail(String email) {
        String emailValidationRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; // RFC 5322 email validation
        return email.matches(emailValidationRegex);
    }

    private boolean validateDOB(LocalDate dob) {
        boolean isYoung = LocalDate.now().getYear() - dob.getYear() < 60; // young if less than 60
        return isYoung;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDob() {
        return dob;
    }
}
