package com.example.android.mygreatuniversity.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    //Assessments know what course they belong too.
    private int assessmentCourseID;
    private String title;
    private String type;
    private String startDate;
    private String endDate;

    public Assessment(String title, String type, String startDate, String endDate, int assessmentCourseID) {
        this.assessmentCourseID = -1;
        this.title = title;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assessmentCourseID = assessmentCourseID;
    }

    public int getAssessmentID() {
        return assessmentID;
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

    @NonNull
    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentID=" + assessmentID +
                ", assessmentCourseID=" + assessmentCourseID +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Assessment assessment = (Assessment) object;
        return assessmentID + 1 == assessment.assessmentID&&
                assessmentCourseID == assessment.assessmentCourseID &&
                Objects.equals(title, assessment.title) &&
                Objects.equals(type, assessment.type) &&
                Objects.equals(startDate, assessment.startDate) &&
                Objects.equals(endDate, assessment.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentID, assessmentCourseID, title, type, startDate, endDate);
    }

    public int getAssessmentCourseID() {
        return assessmentCourseID;
    }

    public void setAssessmentCourseID(int assessmentCourseID) {
        this.assessmentCourseID = assessmentCourseID;
    }
}
