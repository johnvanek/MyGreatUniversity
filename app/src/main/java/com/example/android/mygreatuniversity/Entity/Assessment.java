package com.example.android.mygreatuniversity.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private String title;
    private String type;
    private String startDate;
    private String endDate;

    public Assessment(String title, String type, String startDate, String endDate) {
        this.title = title;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseID = -1;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentID=" + assessmentID +
                ", courseID=" + courseID +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
