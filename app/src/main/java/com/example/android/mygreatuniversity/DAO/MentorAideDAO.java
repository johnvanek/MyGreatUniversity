package com.example.android.mygreatuniversity.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.mygreatuniversity.Entity.MentorAide;
import com.example.android.mygreatuniversity.Entity.TechSupport;

import java.util.List;

    @Dao
    public interface MentorAideDAO {
        @Insert
        void insertMentorAide(MentorAide mentorAide);

        @Query("SELECT * FROM mentorAides")
        List<MentorAide> getAllMentorAides();

        @Query("SELECT * FROM mentorAides WHERE LOWER(name) LIKE LOWER(:name || '%') ORDER BY name ASC")
        List<MentorAide> getMentorAidesByName(String name);

        @Query("SELECT * FROM mentorAides WHERE LOWER(subjects) LIKE LOWER(:subject || '%')")
        List<MentorAide> getMentorAidesBySubjects(String subject);

        @Query("SELECT * FROM mentorAides WHERE ',' || LOWER(availability) || ',' LIKE LOWER('%,' || :days || ',%')")
        List<MentorAide> getMentorAidesByAvailability(String days);
}
