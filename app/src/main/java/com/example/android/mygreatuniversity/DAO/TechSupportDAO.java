package com.example.android.mygreatuniversity.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.mygreatuniversity.Entity.TechSupport;

import java.util.List;

@Dao
public interface TechSupportDAO {
    @Insert
    void insertMentorAide(TechSupport techSupport);

    @Query("SELECT * FROM techSupport")
    List<TechSupport> getAllTechSupport();

    @Query("SELECT * FROM techSupport WHERE name = :name")
    List<TechSupport> getTechSupportByName(String name);

    @Query("SELECT * FROM techSupport WHERE :operatingSystems IN (operatingSystems)")
    List<TechSupport> getTechSupportsByOs(String operatingSystems);
}