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
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.MentorAide;
import com.example.android.mygreatuniversity.Entity.TechSupport;
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.Entity.User;

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
        //TODO Change these to use Repo
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
        System.out.println(course.toString() + " Compared to " + insertedCourse.toString());
    }

    @Test
    public void insertTerm() throws Exception {
        Term term = new Term("Spring2021", "6/01/2021", "12/31/2021");
        //Call the method to insert a Term
        termDAO.insertTerm(term);
        //Get the first Term there should only be one
        Term insertedTerm = termDAO.getTerms().get(0);
        //Assert equal
        assertEquals(term, insertedTerm);
        System.out.println(term.toString() + " Compared to " + insertedTerm.toString());
    }

    @Test
    public void insertMentor() throws Exception {
        Mentor mentor = new Mentor("Sarah Conor", "561-123-1991", "ResistSky@gmail.com");
        //Call the method to insert a Mentor
        mentorDAO.insertMentor(mentor);
        //Get the first Mentor there should only be one
        Mentor insertedMentor = mentorDAO.getMentors().get(0);
        //Assert equal
        assertEquals(mentor, insertedMentor);
        System.out.println(mentor.toString() + " Compared to " + insertedMentor.toString());
    }

    @Test
    public void insertAssessment() throws Exception {
        Assessment assessment = new Assessment("Android App Dev", "Performance", "10/15/2022", "10/30/2022", 1);
        //Call the method to insert a Assessment
        assessmentDAO.insertAssessment(assessment);
        //Get the first Assessment there should only be one
        Assessment insertedAssessment = assessmentDAO.getAssessments().get(0);
        //Assert equal
        assertEquals(assessment, insertedAssessment);
        System.out.println(assessment.toString() + " Compared to " + insertedAssessment.toString());
    }

    @Test
    public void insertUser() throws Exception {
        User user = new User("Jane Doe", "janeDoe", "test");
        //Call the method to insert a User
        userDAO.insertUser(user);
        //Get the first User there should only be one
        User insertedUser = userDAO.getUsers().get(0);
        //Assert equal
        assertEquals(user, insertedUser);
        System.out.println(user.toString() + " Compared to " + insertedUser.toString());
    }

    @Test
    public void insertMentorAide() throws Exception {
        MentorAide mentorAide = new MentorAide("Sophia Bennett","MGUSB@gmail.com",
                "Subject Assistant","Tuesday,Wednesday","Mobile App " +
                "Development,Algorithms and Data Structures");
        //Call the method to insert a MentorAide
        mentorAideDAO.insertMentorAide(mentorAide);
        //Get the first MentorAide there should only be one
        MentorAide insertedMentorAide = mentorAideDAO.getAllMentorAides().get(0);
        //Assert equal
        assertEquals(mentorAide, insertedMentorAide);
        System.out.println(mentorAide.toString() + " Compared to " + insertedMentorAide.toString());
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