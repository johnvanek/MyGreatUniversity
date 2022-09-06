package com.example.android.mygreatuniversity.Database;

import android.app.Application;
import android.util.Log;

import com.example.android.mygreatuniversity.DAO.AssessmentDAO;
import com.example.android.mygreatuniversity.DAO.CourseDAO;
import com.example.android.mygreatuniversity.DAO.CourseMentorDAO;
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repo {
    private CourseDAO mCourseDAO;
    private CourseMentorDAO mCourseMentorDAO;
    private AssessmentDAO mAssessmentDAO;
    private List<Course> mCourses;
    private List<Mentor> mMentors;
    private List<Assessment> mAssessments;
    private Mentor mMentor;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repo(Application app) {
        DatabaseBuilder dbb = DatabaseBuilder.getDatabase(app);
        mCourseDAO = dbb.courseDAO();
        mCourseMentorDAO = dbb.mentorDAO();
        mAssessmentDAO = dbb.assessmentDAO();
    }
    //Course Methods
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
    public void deleteCourse(Course course){
        executor.execute(() -> mCourseDAO.deleteCourse(course));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };
    //Mentor Methods
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
    public void deleteMentor(Mentor mentor) {
        executor.execute(() -> mCourseMentorDAO.deleteMentor(mentor));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Mentor findMentorById(Integer id) {
        executor.execute(() -> mCourseMentorDAO.findMentorById(id));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  mMentor;
    }
    //Assessment Methods
    public void insertAssessment(Assessment assessment) {
        executor.execute(() -> mAssessmentDAO.insertAssessment(assessment));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void updateAssessment(Assessment assessment) {
        executor.execute(() -> mAssessmentDAO.updateAssessment(assessment));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void deleteAssessment(Assessment assessment) {
        executor.execute(() -> mAssessmentDAO.deleteAssessment(assessment));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Assessment> getAssessments() {
        executor.execute(() -> mAssessmentDAO.getAssessments());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssessments;
    }
}
