package com.example.android.mygreatuniversity.Database;

import android.content.Context;

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
@Database(entities = {Course.class, Mentor.class, Assessment.class}, version = 23, exportSchema = false)

public abstract class DatabaseBuilder extends RoomDatabase {
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
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    class DummyData extends RoomDatabase.Callback {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //TODO add all the dummy data here that I want to insert into the application anyting
            // That is inserted here is only going to run once.
            db.beginTransaction();
            db.execSQL("INSERT INTO Sport('sportName','gender','sportType') VALUES(?,?,?)",new Object[]{"Basketball","BOTH","TEAM"});
            db.execSQL("INSERT INTO Sport('sportName','gender','sportType') VALUES(?,?,?)",new Object[]{"Football","MALE","TEAM"});
            db.execSQL("INSERT INTO Sport('sportName','gender','sportType') VALUES(?,?,?)",new Object[]{"Ping Pong","BOTH","SINGLE"});
            db.endTransaction();
        }
    }
}


