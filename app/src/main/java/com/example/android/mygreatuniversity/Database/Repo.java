package com.example.android.mygreatuniversity.Database;

import android.app.Application;

import com.example.android.mygreatuniversity.DAO.CourseDAO;
import com.example.android.mygreatuniversity.Entity.Course;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repo {
    private CourseDAO mCourseDAO;
    private List<Course> mCourses;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repo(Application app) {
        DatabaseBuilder dbb = DatabaseBuilder.getDatabase(app);
        mCourseDAO = dbb.courseDAO();
    }

    public void insert(Course course) {
        executor.execute(() -> mCourseDAO.insert(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Course> getCourses() {
        executor.execute(() -> mCourses = mCourseDAO.getCourses());
        return mCourses;
    }
}
