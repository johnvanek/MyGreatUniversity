package com.example.android.mygreatuniversity.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "courses")

public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String title;
    private String startDate;
    private String endDate;
    private String status;
    private int courseMentorId;
    private int termID;
    private String courseNotes;

    public Course(String title, String startDate, String endDate, String status, int courseMentorId, int termID, String courseNotes) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.courseMentorId = courseMentorId;
        this.termID = termID;
        //This initializes the notes to an empty value so we don't get null.
        this.courseNotes = courseNotes;
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

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    @NonNull
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

    @Override
    public boolean equals(Object object) {
        //If the same object
        if (this == object) return true;
        //If object is null or not the same Class
        if (object == null || getClass() != object.getClass()) return false;
        Course course = (Course) object;
        //Need to decrement this courseID by one because the DB auto-increments from one
        //And the test starts from 0 leading to a mismatch else remove courseID comparison.
        return  (courseID + 1 == course.courseID &&
                courseMentorId == course.courseMentorId &&
                termID == course.termID &&
                Objects.equals(title, course.title) &&
                Objects.equals(startDate, course.startDate) &&
                Objects.equals(endDate, course.endDate) &&
                Objects.equals(status, course.status) &&
                Objects.equals(courseNotes, course.courseNotes));
    }

    //Generates hashCode based on the objects contents putting them in the same bucket.
    @Override
    public int hashCode() {
        return Objects.hash(courseID, title, startDate, endDate, status, courseMentorId, termID, courseNotes);
    }
}
