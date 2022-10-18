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
        //TODO create Assessment View.class the UI should be similar to a list
        Intent intent = new Intent(MainActivity.this, AssessmentView.class);
        startActivity(intent);
    }

    private void InsertDummyData() {
        // Call concrete method from a DAO here in order to get the pre-population
        // For the database working.
        //TODO ADD to this method when assessment creation is enabled to create dummy data for that
        // as well.

        Repo repo = new Repo(getApplication());
        Log.d("DUMMYDATA", "The courses in the database before dummy data are " + repo.getCourses());
        // If there are no courses insert some dummy data
        if(repo.getCourses().size() <= 0) {
            //******Courses*******
            // Date Format is MM/dd/YY
            //Values for Status
            // In-Progress -- Completed -- Dropped -- Plan To Take

            //******Courses******
            repo.insertCourse(new Course("Mobile Development", "10/01/22","10/30/22","In Progress",1,1));
            repo.insertCourse(new Course("Operating Systems", "06/01/22", "07/30/22", "Completed",2,1));
            repo.insertCourse(new Course("Java Fundamentals", "08/01/22", "08/30/22", "Completed",3,1));
            repo.insertCourse(new Course("Javascript Basics", "12/01/22", "01/30/22", "Plan To Take",4,1));
            repo.insertCourse(new Course("Design Patterns", "07/01/22", "07/15/22", "Dropped",5,1));

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

            //******Assessments******
            // TODO need to refactor this insert statement so that it knows which course it belong to.
            //I believe that this is correct in that it is not index based and that it starts from 1.

            repo.insertAssessment(new Assessment("Android App Dev","Performance","10/15/22","10/30/22",1));
            repo.insertAssessment(new Assessment("OSx86 Basics","Objective","06/15/22","07/30/22",2));
            repo.insertAssessment(new Assessment("Java App-1","Performance","08/15/22","08/30/22",3));
            repo.insertAssessment(new Assessment("ECMAScript Cert.","Objective","12/15/22","01/30/22",4));
            repo.insertAssessment(new Assessment("GangOfFour Test","Objective","07/15/22","08/30/22",5));


            Log.d("DUMMYCOURSE", "The Dummy courses are " + repo.getCourses());
            Log.d("DUMMYMENTOR", "The Dummy mentors are " + repo.getMentors());
            Log.d("DUMMYASSESSMENT", "The Dummy assessments are " + repo.getAssessments());
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