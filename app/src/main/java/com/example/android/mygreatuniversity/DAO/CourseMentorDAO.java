package com.example.android.mygreatuniversity.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.mygreatuniversity.Entity.Mentor;

import java.util.List;
@Dao
public interface CourseMentorDAO {
    @Query("SELECT * FROM mentors")
    List<Mentor> getMentors();

    @Query("SELECT * FROM mentors WHERE mentorID LIKE :mentorID LIMIT 1")
    Mentor findMentorById(int mentorID);

    @Insert
    void insertMentor(Mentor mentor);

    @Update
    void updateMentor(Mentor mentor);

    @Delete
    void delete(Mentor mentor);
}
