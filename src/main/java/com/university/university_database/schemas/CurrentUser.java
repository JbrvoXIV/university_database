package com.university.university_database.schemas;

public class CurrentUser {
    private static Person user;

    public static Person getUser() {
        return user;
    }

    public static void setUser(Person user) {
        CurrentUser.user = user;
    }
}
