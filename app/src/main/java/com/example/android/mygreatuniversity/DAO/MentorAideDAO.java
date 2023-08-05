package com.example.android.mygreatuniversity.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.mygreatuniversity.Entity.MentorAide;

import java.util.List;

    @Dao
    public interface MentorAideDAO {
        @Insert
        void insertMentorAide(MentorAide mentorAide);

        @Query("SELECT * FROM mentorAides")
        List<MentorAide> getAllMentorAides();

        @Query("SELECT * FROM mentorAides WHERE name = :name")
        List<MentorAide> getMentorAidesByName(String name);

        @Query("SELECT * FROM mentorAides WHERE :subject IN (subjects)")
        List<MentorAide> getMentorAidesBySubject(String subject);

}
