package com.university.university_database.schemas;

import java.sql.Time;
import java.time.LocalDate;

public class Course {
    private String courseID;
    private int professorID;
    private String courseName;
    private String professorName;
    private Time startTime;
    private Time endTime;
    private String roomNumber;
    private String grade;

    public Course(String courseID, int professorID, String courseName, String professorName, Time startTime, Time endTime, String roomNumber, String grade) {
        this.courseID = courseID;
        this.professorID = professorID;
        this.courseName = courseName;
        this.professorName = professorName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomNumber = roomNumber;
        this.grade = grade;
    }

    public String getCourseID() {
        return courseID;
    }

    public int getProfessorID() {
        return professorID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getGrade() {
        return grade;
    }
}
