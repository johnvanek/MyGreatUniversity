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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // This disables the night mode which changes the color the text to be unreadable.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Sets the toolbar defined in the layout as the action bar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Insert the Dummy Data
        //Comment this out in order to not inset dummyData ever.
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
        //TODO set the subheading of each page to modern governess university
        // Either in the xml or by setting it onCreate

        //TODO implement Terms
        // 1. Create a user option to enter term titles (e.g., Term 1, Term 2, Spring Term), start dates, and end dates for each term.
        // 2. Create features that allow the user to add as many terms as needed.
        // 3. Implement validation so that a term cannot be deleted if courses are assigned to it.
        // 4. Create features that allow the user to do the following for each term:
        //      a. Add as many courses as needed
        //          (Need to migrate most of the logic that was written for courses to Terms)
        //          ( Aka Courses should be show inside of terms )
        //      b. Display a list of Courses associated with each term
        //      c. Display a detailed view of the term title (e.g., Term 1, Term 2, Spring Term), the start date, and the end date for each term


        //TODO in order to implement Terms
        // Going to need to create an adapter like with Courses.
        // Should start off by showing a list of terms that goes to the detailed view.
        //

        //TODO for the detailed view of Terms
        // Show a relevant list of courses
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

    private void InsertDummyData() {
        // Call concrete method from a DAO here in order to get the pre-population
        // For the database working.
        //TODO ADD to this method when assessment/ Term functionality is added.
        Repo repo = new Repo(getApplication());
        Log.d("DUMMYDATA", "The courses in the database before dummy data are " + repo.getCourses());
        // If there are no courses insert some dummy data
        if(repo.getCourses().size() <= 0) {
            //******Courses*******
            // Date Format is MM/dd/YY
            //Values for Status
            // In-Progress -- Completed -- Dropped -- Plan To Take

            //TODO need to figure out a way to reimplement this so that
            // The terms need to be associated with Courses
            // So That when The Date-Picker for Courses needs to be a Subset of the DateRange from
            // Term which would be its parent ( Or else the Course can pick dates out of Range )
            //
            //TODO rework this dummy data so that it makes sense with assessments.

            repo.insertCourse(new Course("Mobile Development", "10/01/22","10/30/22","In Progress",1,1));
            repo.insertCourse(new Course("Operating Systems", "06/01/22", "07/30/22", "Completed",2,1));
            repo.insertCourse(new Course("Java Fundamentals", "08/01/22", "08/30/22", "Completed",3,1));
            repo.insertCourse(new Course("Javascript Basics", "12/01/22", "01/30/22", "Plan To Take",4,1));
            repo.insertCourse(new Course("Design Patterns", "07/01/22", "07/15/22", "Dropped",5,1));

            //TODO determine if the functionality for mentors would still work if relocated.
            //******Mentors******
            //Should always be at least one or else Course view detailed will throw an error.
            repo.insertMentor(new Mentor("Sarah Conor", "561-123-1991", "ResistSky@gmail.com"));
            repo.insertMentor(new Mentor("Lisa Lisa", "039-123-1987", "HamonMaster@gmail.com"));
            repo.insertMentor(new Mentor("Professor Xavier", "010-603-1963", "XMen@gmail.com"));
            repo.insertMentor(new Mentor("Dan Abramov", "203-898-2085", "DanMov@gmail.com"));
            repo.insertMentor(new Mentor("Robert Martin", "943-185-3814", "DanMov@gmail.com"));
            //******Terms******
            repo.insertTerm(new Term("Spring2021","6/01/21","12/31/21"));
            repo.insertTerm(new Term("Fall2022","01//01/22","5/31/22"));
            repo.insertTerm(new Term("Spring2022","6/01/22","12/31/22"));
            repo.insertTerm(new Term("Fall2023","01//01/23","5/31/23"));

            Log.d("DUMMYCOURSE", "The Dummy courses are " + repo.getCourses());
            Log.d("DUMMYMENTOR", "The Dummy mentors are " + repo.getMentors());
        }
    }

    //TODO
    // B.  Design the following screen layouts, including appropriate GUI (graphical user interface)
    // elements (e.g., navigation, input, and information) for each layout:
    //  home screen
    //  list of terms
    //  list of courses
    //  list of assessments
    //  detailed course view
    //  detailed term view
    //  detailed assessment view
}