package com.example.android.mygreatuniversity.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "mentors")
public class Mentor {
    @PrimaryKey(autoGenerate = true)
    private int mentorID;
    private String name;
    private String phoneNumber;
    private String email;

    public Mentor(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Mentor mentor = (Mentor) object;
        return mentorID + 1== mentor.mentorID&&
                Objects.equals(name, mentor.name) &&
                Objects.equals(phoneNumber, mentor.phoneNumber) &&
                Objects.equals(email, mentor.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mentorID, name, phoneNumber, email);
    }
    @NonNull
    @Override
    public String toString() {
        return "Mentor{" +
                "mentorID=" + mentorID +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
