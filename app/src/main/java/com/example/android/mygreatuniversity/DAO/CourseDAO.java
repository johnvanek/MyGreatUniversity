package com.example.android.mygreatuniversity.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.mygreatuniversity.Entity.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    //What to do if already course already exists.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<Course> getCourses();
}
