package com.university.university_database.schemas;

public abstract class Person {

    protected int ID;
    protected String password;
    protected int departmentID;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phone;
    protected String email;

    public Person(String ID, String password, int departmentID, String firstName, String lastName, String address, String phone, String email) {
        IllegalArgumentException e = validateInputs(ID, password, firstName, lastName, address, phone, email);

        if(e != null) {
            throw e; // error was found in inputs
        }

        this.ID = Integer.parseInt(ID);
        this.password = password;
        this.departmentID = departmentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    private IllegalArgumentException validateInputs(String ID, String password, String firstName, String lastName, String address, String phone, String email) {
        if(!validateID(ID)) {
            return new IllegalArgumentException("The ID value you entered, " + ID + ", is incorrect! Please try again.");
        }
        if(!validatePassword(password)) {
            if(password.isEmpty())
                return new IllegalArgumentException("Cannot submit an empty password, please input something.");
            return new IllegalArgumentException("The password value you entered, " + password + ", is too long! Please try again.");
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

    private boolean validatePassword(String password) {
        boolean isCorrectLength = password.length() <= 50 && !password.isEmpty();
        return isCorrectLength;
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

    public int getID() {
        return ID;
    }

    public String getPassword() {
        return password;
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

    public int getDepartmentID() {
        return departmentID;
    }
}
