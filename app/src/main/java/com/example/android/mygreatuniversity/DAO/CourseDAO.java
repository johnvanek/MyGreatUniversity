package com.example.android.mygreatuniversity.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCourse(Course course);



    @Update()
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<Course> getCourses();

    @Query("SELECT * FROM courses WHERE courseID LIKE :courseID LIMIT 1")
    Course findCourseById(int courseID);

    @Query("SELECT * FROM mentors WHERE mentorID LIKE :mentorID LIMIT 1")
    Mentor findMentorById(int mentorID);

    @Query("SELECT * FROM courses WHERE termID LIKE :termID")
    List<Course> getTermCourses(int termID);
}
