package com.example.android.mygreatuniversity.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentors")
public class Mentor {
    @PrimaryKey(autoGenerate = true)
    private int mentorID;
    private int courseID;
    private String name;
    private String phoneNumber;
    private String email;

    public Mentor(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.courseID = -1;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "Mentor{" +
                "mentorID=" + mentorID +
                ", courseID=" + courseID +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
