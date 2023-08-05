package com.example.android.mygreatuniversity.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "techSupport")
public class TechSupport extends FacultyMisc {
    @PrimaryKey(autoGenerate = true)
    private int techSupportID;
    private String operatingSystems;

    public TechSupport(String name, String email, String jobTitle, String availability, String os) {
        super(name, email, jobTitle, availability);
        this.operatingSystems = os;
    }

    public int getTechSupportID() {
        return techSupportID;
    }

    public void setTechSupportID(int techSupportID) {
        this.techSupportID = techSupportID;
    }

    public String getOperatingSystems() {
        return operatingSystems;
    }

    public void setOperatingSystems(String operatingSystems) {
        this.operatingSystems = operatingSystems;
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
                ", subjects='" + getOperatingSystems() + '\'' +
                '}';
    }
}

