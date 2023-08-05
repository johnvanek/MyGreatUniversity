package com.example.android.mygreatuniversity.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android.mygreatuniversity.DAO.AssessmentDAO;
import com.example.android.mygreatuniversity.DAO.CourseDAO;
import com.example.android.mygreatuniversity.DAO.CourseMentorDAO;
import com.example.android.mygreatuniversity.DAO.TermDAO;
import com.example.android.mygreatuniversity.DAO.UserDAO;
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.Entity.User;


//One database to create another table inset a , after the first class.
// Every time there is a change made to a entity file, the version needs to be incremented.
// Also to clean out the database increment the version
// The DummyData Callback should run once on database creation
// Because this is Instance being returned there are problems trigger overridden OnCreate and OnOpen
// DummyData will instead be handled in the Main activity via a method call in onCreate.
// SomeTimes incrementing this will Re-trigger the DummyData in combination with deleting in from the device.

@Database(entities = {Course.class, Mentor.class, User.class, Assessment.class, Term.class}, version = 51, exportSchema = false)

public abstract class DatabaseBuilder extends RoomDatabase {
    /**
     * Must have the DAO in order to use the crud operations provided by room.
     * // Return the following DAO
     * // Increment the database in order to rebuild the database
     */
    @SuppressWarnings("WeakerAccess")
    public abstract CourseDAO courseDAO();
    public abstract CourseMentorDAO mentorDAO();

    public abstract UserDAO userDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract TermDAO termDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseBuilder.class, "MGUDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


