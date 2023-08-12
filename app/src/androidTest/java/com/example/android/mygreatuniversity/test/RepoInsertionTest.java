package com.example.android.mygreatuniversity.test;

import static org.junit.Assert.assertEquals;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.mygreatuniversity.DAO.AssessmentDAO;
import com.example.android.mygreatuniversity.DAO.CourseDAO;
import com.example.android.mygreatuniversity.DAO.CourseMentorDAO;
import com.example.android.mygreatuniversity.DAO.MentorAideDAO;
import com.example.android.mygreatuniversity.DAO.TechSupportDAO;
import com.example.android.mygreatuniversity.DAO.TermDAO;
import com.example.android.mygreatuniversity.DAO.UserDAO;
import com.example.android.mygreatuniversity.Database.DatabaseBuilder;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.TechSupport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class RepoInsertionTest {

    private CourseDAO courseDAO;
    private TermDAO termDAO;
    private CourseMentorDAO mentorDAO;
    private AssessmentDAO assessmentDAO;
    private UserDAO userDAO;
    private MentorAideDAO mentorAideDAO;
    private TechSupportDAO techSupportDAO;

    private DatabaseBuilder dbb;

    //Assign the DAO fields & build the database
    @Before
    public void createDb() {
        dbb = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DatabaseBuilder.class).allowMainThreadQueries().build();
        courseDAO = dbb.courseDAO();
        termDAO = dbb.termDAO();
        mentorDAO = dbb.mentorDAO();
        assessmentDAO = dbb.assessmentDAO();
        userDAO = dbb.userDAO();
        mentorAideDAO = dbb.mentorAideDAO();
        techSupportDAO = dbb.techSupportDAO();
    }

    //Close the Connection to the Database when we are done.
    @After
    public void closeDb() throws IOException {
        dbb.close();
    }

    @Test
    public void insertCourse() throws Exception {
        Course course = new Course("Mobile Development", "10/01/2022", "10/30/2022", "In Progress", 1, 1, "");
        //Call the method to insert a Course
        courseDAO.insertCourse(course);
        //Get the first Course there should only be one
        Course insertedCourse = courseDAO.getCourses().get(0);
        //Assert equal
        assertEquals(course, insertedCourse);
    }

    @Test
    public void insertTechSupport() throws Exception {
        TechSupport techSupport = new TechSupport("John Doe", "John@gmail.com", "unemployed", "W/E", "Linux");
        //Call the method to insert a Tech
        techSupportDAO.insertTechSupport(techSupport);
        //Get the List of Techs that match this name there should only be one and return the first
        // Result.
        TechSupport insertedTechSupport = techSupportDAO.getAllTechSupport().get(0);
        // Assert Equals
        assertEquals(techSupport, insertedTechSupport);
        System.out.println(techSupport.toString() + " Compared to " + insertedTechSupport.toString());
    }
}