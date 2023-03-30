package com.university.univerity_database.schemas;

public enum Table {
    STUDENT("STUDENT"),
    PROFESSOR("PROFESSOR"),
    ENROLLMENT("ENROLLMENT"),
    SCHEDULE("SCHEDULE"),
    COURSES("COURSES"),
    DEPARTMENT("DEPARTMENT");

    private final String table;

    Table(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }
}
