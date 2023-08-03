package com.example.android.mygreatuniversity.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.R;

public class MainActivity extends AppCompatActivity {
    //TODO
    // Create new entity faculty -> base class -> other staff extends from this class
    // Rework other Entity Classes so that they inherit base properties from Faculty
    // Add a new Class in Entity to Round out the Faculty.
    // This meets the requirement for polymorphism
    // Document the poly in Part C Documentation

    //TODO
    // Create the Login Screen Feature
    // Probably faster to re-implement this custom.
    // Scrap From Old Project
    // Scrap From Github Pull Old C195 project for login
    // Copy over old Resources needed for the login screen.
    // Just import Resources the rest of the code won't import or open on this PC

    //TODO Implement Login Auth
    // Auth -> the User via the login screen
    // On Enter or button press.
    // Implement Method against database to check -> Username -> Password
    // Against Database should check JDBC method might already exist.

    //TODO Document for Security in Part C the Auth method against the local Database.

    public static int notificationAlertCount;
    //test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Token test take 2 with renewed credentials
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // This disables the night mode which changes the color the text to be unreadable.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Sets the toolbar defined in the layout as the action bar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Insert the Dummy Data the logic for generation is contained within.
        InsertDummyData();
    }

    //Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void gotoTermView(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, TermView.class);
        startActivity(intent);
    }

    public void gotoMentorView(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, MentorView.class);
        startActivity(intent);
    }

    public void gotoCourseView(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, CourseView.class);
        startActivity(intent);
    }

    public void gotoAssessmentView(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AssessmentView.class);
        startActivity(intent);
    }
    //-----------||||||||||||||DUMMY-DATA-INSERTION|||||||||||||||||||-----------------*************
    //*************Disable this if you do not want dummy data to inset into the Application*********
    private void InsertDummyData() {
        //Script that will insert dummy data into the application.
        Repo repo = new Repo(getApplication());
        Log.d("DUMMYDATA", "The courses in the database before dummy data are " + repo.getCourses());
        // If there are no courses insert some dummy data
        if(repo.getCourses().size() <= 0) {
            //******Courses*******
            // Date Format is MM/dd/YY
            //Values for Status
            // In-Progress -- Completed -- Dropped -- Plan To Take

            //******Courses******
            repo.insertCourse(new Course("Mobile Development", "10/01/2022","10/30/2022","In Progress",1,1, ""));
            repo.insertCourse(new Course("Operating Systems", "06/01/2022", "07/30/2022", "Completed",2,1, ""));
            repo.insertCourse(new Course("Java Fundamentals", "08/01/2022", "08/30/2022", "Completed",3,1, ""));
            repo.insertCourse(new Course("Javascript Basics", "12/01/2022", "01/30/2022", "Plan To Take",4,1, ""));
            repo.insertCourse(new Course("Design Patterns", "07/01/2022", "07/15/2022", "Dropped",5,1, ""));

            //******Mentors******
            //Should always be at least one or else Course view detailed will throw an error.
            repo.insertMentor(new Mentor("Sarah Conor", "561-123-1991", "ResistSky@gmail.com"));
            repo.insertMentor(new Mentor("Lisa Lisa", "039-123-1987", "HamonMaster@gmail.com"));
            repo.insertMentor(new Mentor("Professor Xavier", "010-603-1963", "XMen@gmail.com"));
            repo.insertMentor(new Mentor("Dan Abramov", "203-898-2085", "DanMov@gmail.com"));
            repo.insertMentor(new Mentor("Robert Martin", "943-185-3814", "BobM@gmail.com"));
            //******Terms******
            repo.insertTerm(new Term("Spring2021","6/01/2021","12/31/2021"));
            repo.insertTerm(new Term("Fall2022","01//01/2022","5/31/2022"));
            repo.insertTerm(new Term("Spring2022","6/01/2022","12/31/2022"));
            repo.insertTerm(new Term("Fall2023","01//01/2023","5/31/2023"));
            //******Assessments******

            repo.insertAssessment(new Assessment("Android App Dev","Performance","10/15/2022","10/30/2022",1));
            repo.insertAssessment(new Assessment("OSx86 Basics","Objective","06/15/2022","07/30/2022",2));
            repo.insertAssessment(new Assessment("Java App-1","Performance","08/15/2022","08/30/2022",3));
            repo.insertAssessment(new Assessment("ECMAScript Cert.","Objective","12/15/2022","01/30/2022",4));
            repo.insertAssessment(new Assessment("GangOfFour Test","Objective","07/15/2022","08/30/2022",5));

            Log.d("DUMMYCOURSE", "The Dummy courses are " + repo.getCourses());
            Log.d("DUMMYMENTOR", "The Dummy mentors are " + repo.getMentors());
            Log.d("DUMMYASSESSMENT", "The Dummy assessments are " + repo.getAssessments());
        }
    }
}