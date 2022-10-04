package com.example.android.mygreatuniversity.Database;

import android.app.Application;
import android.util.Log;

import com.example.android.mygreatuniversity.DAO.AssessmentDAO;
import com.example.android.mygreatuniversity.DAO.CourseDAO;
import com.example.android.mygreatuniversity.DAO.CourseMentorDAO;
import com.example.android.mygreatuniversity.DAO.TermDAO;
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repo {
    private CourseDAO mCourseDAO;
    private CourseMentorDAO mCourseMentorDAO;
    private AssessmentDAO mAssessmentDAO;
    private TermDAO mTermDAO;
    private List<Course> mCourses;
    private List<Mentor> mMentors;
    private List<Assessment> mAssessments;
    private List<Term> mTerms;
    private Mentor mCourseMentor;
    private List<Course> mTermCourses;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repo(Application app) {
        DatabaseBuilder dbb = DatabaseBuilder.getDatabase(app);
        mCourseDAO = dbb.courseDAO();
        mCourseMentorDAO = dbb.mentorDAO();
        mAssessmentDAO = dbb.assessmentDAO();
        mTermDAO = dbb.termDAO();
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
    }
    public Mentor findMentorById(int mentorId){
        executor.execute(() -> {
            mCourseMentor = mCourseMentorDAO.findMentorById(mentorId);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("adapter", "In Repo attempting to get the course mentor " + mCourseMentor);
        return mCourseMentor;
    }
    public List<Course> getTermCourses(int termID) {
        executor.execute(() -> {
            mTermCourses = mCourseDAO.getTermCourses(termID);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("adapter", "In Repo attempting to get courses for a selected Term " + mTermCourses);
        return mTermCourses;
    }

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
    //Term Methods
    public List<Term> getTerms() {
        executor.execute(() -> {
            mTerms = mTermDAO.getTerms();
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("adapter", "In Repo attempting to get terms " + mTerms);
        return mTerms;
    }
    public void insertTerm(Term term) {
        executor.execute(() -> mTermDAO.insertTerm(term));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void updateTerm(Term term) {
        executor.execute(() -> mTermDAO.updateTerm(term));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void deleteTerm(Term term){
        executor.execute(() -> mTermDAO.deleteTerm(term));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
