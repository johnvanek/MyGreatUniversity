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
    //TODO change these the search function is way to accepting of matching content
    @Query("SELECT * FROM techSupport")
    List<TechSupport> getAllTechSupport();

    @Query("SELECT * FROM techSupport WHERE LOWER(name) LIKE LOWER(:searchTerm || '%') ORDER BY name ASC")
    List<TechSupport> getTechSupportByName(String searchTerm);


    @Query("SELECT * FROM techSupport WHERE LOWER(operatingSystems) LIKE LOWER('%' || :os || '%')")
    List<TechSupport> getTechSupportByOs(String os);

    @Query("SELECT * FROM techSupport WHERE ',' || LOWER(availability) || ',' LIKE LOWER('%,' || :day || ',%')")
    List<TechSupport> getTechSupportByAvailability(String day);
}