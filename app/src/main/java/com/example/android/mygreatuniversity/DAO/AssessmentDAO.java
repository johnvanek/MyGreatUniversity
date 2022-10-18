package com.example.android.mygreatuniversity.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;

import java.util.List;

@Dao
public interface AssessmentDAO {
    //What to do if already course already exists.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAssessment(Assessment assessment);

    @Update()
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<Assessment> getAssessments();

    @Query("SELECT * FROM assessments WHERE assessmentCourseID LIKE :courseID")
    List<Assessment> getCourseAssessments(int courseID);
}
