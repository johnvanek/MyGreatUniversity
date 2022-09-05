package com.example.android.mygreatuniversity.Database;

import android.app.Application;
import android.util.Log;

import com.example.android.mygreatuniversity.DAO.CourseDAO;
import com.example.android.mygreatuniversity.DAO.CourseMentorDAO;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repo {
    private CourseDAO mCourseDAO;
    private CourseMentorDAO mCourseMentorDAO;
    private List<Course> mCourses;
    private List<Mentor> mMentors;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repo(Application app) {
        DatabaseBuilder dbb = DatabaseBuilder.getDatabase(app);
        mCourseDAO = dbb.courseDAO();
        mCourseMentorDAO = dbb.mentorDAO();
    }
    //Course methods
    public List<Course> getCourses() {
        executor.execute(() -> {
            mCourses = mCourseDAO.getCourses();
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("adapter", "In Repo attempting to get courses " + mCourses);
        return mCourses;
    }
    public void insertCourse(Course course) {
        executor.execute(() -> mCourseDAO.insertCourse(course));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void updateCourse(Course course) {
        executor.execute(() -> mCourseDAO.updateCourse(course));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //Mentor methods
    public List<Mentor> getMentors() {
        executor.execute(() -> {
            mMentors = mCourseMentorDAO.getMentors();
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("adapter", "In Repo attempting to get mentors " + mMentors);
        return mMentors;
    }
    public void insertMentor(Mentor mentor) {
        executor.execute(() -> mCourseMentorDAO.insertMentor(mentor));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void updateMentor(Mentor mentor) {
        executor.execute(() -> mCourseMentorDAO.updateMentor(mentor));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
