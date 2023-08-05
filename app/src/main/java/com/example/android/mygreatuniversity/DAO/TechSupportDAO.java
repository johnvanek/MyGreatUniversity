package com.example.android.mygreatuniversity.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.mygreatuniversity.Entity.TechSupport;

import java.util.List;

@Dao
public interface TechSupportDAO {
    @Insert
    void insertTechSupport(TechSupport techSupport);

    @Query("SELECT * FROM techSupport")
    List<TechSupport> getAllTechSupport();

    @Query("SELECT * FROM techSupport WHERE LOWER(name) LIKE LOWER('%' || :name || '%')")
    List<TechSupport> getTechSupportByName(String name);

    @Query("SELECT * FROM techSupport WHERE operatingSystems LIKE '%' || :os || '%'")
    List<TechSupport> getTechSupportByOs(String os);

    @Query("SELECT * FROM techSupport WHERE availability LIKE '%' || :days || '%'")
    List<TechSupport> getTechSupportByAvailability(String days);
}