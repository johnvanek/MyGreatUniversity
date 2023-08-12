package com.example.android.mygreatuniversity.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Objects;

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
                "mentorAideID=" + mentorAideID +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", jobTitle='" + getJobTitle() + '\'' +
                ", availability=" + getAvailability() +
                ", subjects='" + subjects + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MentorAide mentorAide = (MentorAide) object;
        return mentorAideID + 1 == mentorAide.mentorAideID &&
                Objects.equals(getName(), mentorAide.getName()) &&
                Objects.equals(getEmail(), mentorAide.getEmail()) &&
                Objects.equals(getJobTitle(), mentorAide.getJobTitle()) &&
                Objects.equals(getAvailability(), mentorAide.getAvailability()) &&
                Objects.equals(subjects, mentorAide.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mentorAideID, getName(), getEmail(), getJobTitle(), getAvailability(), subjects);
    }
}
