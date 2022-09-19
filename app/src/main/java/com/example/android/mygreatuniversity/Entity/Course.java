package com.example.android.mygreatuniversity.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
//TODO
// Courses have mentors, so add a field
// int courseMentorId;
// that acts to link the database tables courses and mentors

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private int courseMentorId;
    private String title;
    private String startDate;
    private String endDate;
    private String status;

    public Course(String title, String startDate, String endDate, String status) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getCourseID() {
        return courseID;
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

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseMentorId() {
        return courseMentorId;
    }

    public void setCourseMentorId(int courseMentorId) {
        this.courseMentorId = courseMentorId;
    }
}
