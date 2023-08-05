package com.example.android.mygreatuniversity.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "mentorAides")
public class MentorAide extends FacultyMisc {
    @PrimaryKey(autoGenerate = true)
    private int mentorAideID;
    private String subjects;

    public MentorAide(String name, String email, String jobTitle, String availability, String subjects) {
        super(name, email, jobTitle, availability);
        this.subjects = subjects;
    }

    public int getMentorAideID() {
        return mentorAideID;
    }

    public void setMentorAideID(int mentorAideID) {
        this.mentorAideID = mentorAideID;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    // Overloaded method Contract - For PolyMorphism Requirement
    // toString method Overriding is Runtime Polymorphism
    // Overloading in static compile time Polymorphism
    @NonNull
    @Override
    public String toString() {
        return "Mentor-Aide{" +
                "name='" + super.getName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", jobTitle='" + super.getJobTitle() + '\'' +
                ", availability=" + super.getAvailability() +
                ", subjects='" + getSubjects() + '\'' +
                '}';
    }


}
