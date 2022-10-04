package com.example.android.mygreatuniversity.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "courses")

//TODO modify this class so that classes know what terms they belong in.
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String title;
    private String startDate;
    private String endDate;
    private String status;
    private int courseMentorId;
    private int termID;

    public Course(String title, String startDate, String endDate, String status, int courseMentorId, int termID) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.courseMentorId = courseMentorId;
        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCourseMentorId() {
        return courseMentorId;
    }

    public void setCourseMentorId(int courseMentorId) {
        this.courseMentorId = courseMentorId;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}
