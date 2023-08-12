package com.example.android.mygreatuniversity.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "techSupport")
public class TechSupport extends FacultyMisc {
    @PrimaryKey(autoGenerate = true)
    private int techSupportID;
    private String operatingSystems;

    private long dateHired;

    public TechSupport(String name, String email, String jobTitle, String availability, String operatingSystems) {
        super(name, email, jobTitle, availability);
        this.operatingSystems = operatingSystems;
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
    // Overloading is static compile time Polymorphism
    @NonNull
    @Override
    public String toString() {
        return "Tech-Support{" +
                "name='" + super.getName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", jobTitle='" + super.getJobTitle() + '\'' +
                ", availability=" + super.getAvailability() +
                ", subjects='" + getOperatingSystems() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        TechSupport techSupport = (TechSupport) object;
        //Decrement the ID
        return techSupportID == techSupport.techSupportID -1 &&
                operatingSystems.equals(techSupport.operatingSystems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), techSupportID, operatingSystems);
    }

    public long getDateHired() {
        return dateHired;
    }

    public void setDateHired(long dateHired) {
        this.dateHired = dateHired;
    }
}

