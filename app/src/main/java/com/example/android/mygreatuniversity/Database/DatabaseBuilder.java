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
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;


//One database to create another table inset a , after the first class.
// Every time there is a change made to a entity file, the version needs to be incremented.
// Also to clean out the database increment the version
// The DummyData Callback should run once on database creation
// To Test the OnCreate and OnOpen methods some sort of concrete implementation must be invoked.
// Call A readable method in order to populate

@Database(entities = {Course.class, Mentor.class, Assessment.class}, version = 24, exportSchema = false)

public abstract class DatabaseBuilder extends RoomDatabase {
    /**
     * Must have the DAO in order to use the insert operations
     */
    @SuppressWarnings("WeakerAccess")
    public abstract CourseDAO courseDAO();
    public abstract CourseMentorDAO mentorDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile DatabaseBuilder INSTANCE;


    static DatabaseBuilder getDatabase(final Context context) {
        // TODO Change this to just return an instance of the database not to
        // actually build it like it currently is.

        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseBuilder.class, "MGUDatabase.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(new DummyDataCallBack())
                            .allowMainThreadQueries() //This is just for the initial insertion
                            .build();
                }
            }
        }
        return INSTANCE;
    }

     static class DummyDataCallBack extends RoomDatabase.Callback {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("ONCREATE","Database has been created.");
            //Just test this for now and I need to see inside of the app inspection.
            db.beginTransaction();
            //Rework this here to work with my own dummy data
//            db.execSQL("INSERT INTO Sport('sportName','gender','sportType') VALUES(?,?,?)", new Object[]{"Basketball", "BOTH", "TEAM"});
            db.execSQL("INSERT INTO courses('title', 'startDate', 'endDate', 'status', 'courseMentorId') VALUES(?,?,?,?,?)", new Object[]{"Biology", "10/02/22", "11/01/22", "In-Progress", 1});
            db.endTransaction();
        }
         @Override
         public void onOpen(@NonNull SupportSQLiteDatabase db) {
             super.onOpen(db);
             Log.d("ONOPEN","Database has been opened.");
         }
    }


}


