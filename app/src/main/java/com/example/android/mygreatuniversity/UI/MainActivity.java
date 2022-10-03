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

    private void InsertDummyData() {
        // Call concrete method from a DAO here in order to get the pre-population
        // For the database working.
        //TODO implement this method so that Dummy Data 3 or 5 record gets inserted into the database.
        Repo repo = new Repo(getApplication());
        Log.d("DUMMYDATA", "The courses in the database are " + repo.getCourses());
        //If there are no courses add one
        if(repo.getCourses().size() <= 0) {
            //Insert the Records here
            //Courses
            repo.insertCourse(new Course("Test", "01/01/20","02/02/21","Testing",1));
        }
    }
}