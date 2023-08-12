package com.example.android.mygreatuniversity.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.android.mygreatuniversity.Database.DatabaseBuilder;
import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RepoDeletionTest {

    private DatabaseBuilder dbb;

    private Repo repo;

    //Assign the DAO fields & build the database
    @Before
    public void createDb() {
        dbb = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DatabaseBuilder.class).allowMainThreadQueries().build();
        repo = new Repo(ApplicationProvider.getApplicationContext()); // Build Repo using
    }

    //Close the Connection to the Database when we are done.
    @After
    public void closeDb() throws IOException {
        dbb.close();
    }

    //***************NOTE:**************************
    //This is Asynchronous code in Order for testing to work in the Environment have to use CountDown
    //Latch Sometimes this clogs the Thread which makes the app unresponsive which causes the tests
    // To fail. Testing Suite will kill the Status of the test in the run window due to concurrent locks
    //The threads clog to much state of ran all at once if ran individually they pass.

    @Test
    public void deleteCourse() {
        // Create a new Course
        Course course = new Course("Course Title", "2023-08-01", "2023-12-31", "In Progress", 1, 1, "Notes");

        // Insert course
        repo.insertCourse(course);

        // Then Delete course
        repo.deleteCourse(course);

        //Waits until an operation has been performed
        CountDownLatch latch = new CountDownLatch(1);

        // Creates a new thread then sleep 500millis
        new Thread(() -> {
            try {
                // Sleep for async operation to complete
                //Try Reducing the time of the sleep if the app times out
                Thread.sleep(100); // Sleep Can't just use this have to also include latch

                // Query the database to check if the mentors is deleted
                List<Course> courses = repo.getCourses();

                // Assert that the list of mentors is aka value was deleted
                assertTrue("Failure to Delete Assessment from Database", courses.isEmpty());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown(); // Release the latch
            }
        }).start();

        try {
            latch.await(); // Wait for the asynchronous operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void deleteMentor() {
        // Create a sample mentor
        Mentor mentor = new Mentor("John Doe", "123-456-7890", "mentor@gmail.com");

        // Insert the mentor
        repo.insertMentor(mentor);

        // Then Delete the mentor
        repo.deleteMentor(mentor);

        //Waits until an operation has been performed
        CountDownLatch latch = new CountDownLatch(1);

        // Creates a new thread then sleep 500millis
        new Thread(() -> {
            try {
                // Sleep for async operation to complete
                Thread.sleep(100); // Sleep Can't just use this have to also include latch

                // Query the database to check if the mentors is deleted
                List<Mentor> mentors = repo.getMentors();

                // Assert that the list of mentors is aka value was deleted
                assertTrue("Failure to Delete Assessment from Database", mentors.isEmpty());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown(); // Release the latch
            }
        }).start();

        try {
            latch.await(); // Wait for the asynchronous operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void deleteAssessment() {
        // Create a sample assessment
        Assessment assessment = new Assessment("Assessment Title", "Type", "2023-08-01", "2023-08-15", 1);
        // Insert the assessment into the database
        repo.insertAssessment(assessment);
        // Delete the assessment from the database
        repo.deleteAssessment(assessment);
        //Waits until an operation has been performed
        CountDownLatch latch = new CountDownLatch(1);

        // Creates a new thread then sleep 500millis
        new Thread(() -> {
            try {
                // Sleep for async operation to complete
                Thread.sleep(100); // Sleep Can't just use this have to also include latch

                // Query the database to check if the assessment is deleted
                List<Assessment> assessments = repo.getAssessments();

                // Assert that the list of assessments is aka value was deleted
                assertTrue("Failure to Delete Assessment from Database", assessments.isEmpty());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown(); // Release the latch
            }
        }).start();

        try {
            latch.await(); // Wait for the asynchronous operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void deleteTerm() {
        // Create a sample term
        Term term = new Term("Term Title", "2023-01-01", "2023-12-31");

        // Insert the term
        repo.insertTerm(term);

        // Delete the term
        repo.deleteTerm(term);

        //Waits until an operation has been performed
        CountDownLatch latch = new CountDownLatch(1);

        // Creates a new thread then sleep 500millis
        new Thread(() -> {
            try {
                // Sleep for async operation to complete
                Thread.sleep(100); // Sleep Can't just use this have to also include latch

                // Query the database to check if the assessment is deleted
                Term deletedTerm = repo.lookupTermById(term.getTermID());

                // Assert that the list of assessments is aka value was deleted
                assertNull("Failure to Delete Term from Database", deletedTerm);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown(); // Release the latch
            }
        }).start();

        try {
            latch.await(); // Wait for the asynchronous operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}