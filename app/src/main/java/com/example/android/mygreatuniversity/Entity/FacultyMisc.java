package com.example.android.mygreatuniversity.Entity;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Objects;

// Abstract base class for faculty-misc
public abstract class FacultyMisc {
    private String name;
    private String email;
    private String jobTitle;
    private String availability;

    protected FacultyMisc() {
        this("John Doe", "MGUfaculty@gmail.com", "Misc Faculty", "Monday,Tuesday,Wednesday,Thursday,Friday");
    }

    protected FacultyMisc(String name, String email, String jobTitle, String availability) {
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
        this.availability = availability;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    // Overloaded method Contract - For PolyMorphism Requirement
    // toString method Overriding is Runtime Polymorphism
    // Overloading in static compile time Polymorphism
    @NonNull
    @Override
    public String toString() {
        return "FacultyMisc{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", availability=" + availability +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FacultyMisc miscFaculty = (FacultyMisc) object;
        return Objects.equals(name, miscFaculty.name) &&
                Objects.equals(email, miscFaculty.email) &&
                Objects.equals(jobTitle, miscFaculty.jobTitle) &&
                Objects.equals(availability, miscFaculty.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, jobTitle, availability);
    }
}

