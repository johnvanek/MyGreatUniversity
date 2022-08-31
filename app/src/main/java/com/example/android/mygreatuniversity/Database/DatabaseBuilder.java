package com.example.android.mygreatuniversity.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.mygreatuniversity.DAO.CourseDAO;
import com.example.android.mygreatuniversity.Entity.Course;

//One database to create another table inset a , after the first class.
// Every time there is a change made to a entity file, the version needs to be incremented.
// Also to clean out the database increment the version
@Database(entities = {Course.class}, version = 4, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract CourseDAO courseDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class,
                                    "MGUDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


