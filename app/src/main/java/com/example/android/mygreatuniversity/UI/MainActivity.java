package com.example.android.mygreatuniversity.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sets the toolbar defined in the layout as the action bar
        //Which enables me to use more methods.
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
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

    public void gotoCourseView(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, CourseView.class);
        startActivity(intent);

        //If coming to a new activity from an intent it refreshed the whole page or maybe use
        // this.finish(). If intending to end a page activity.

        //Test out adding to the database here
        //This is how you would add a new course
        Repo repo = new Repo(getApplication());
        Course course = new Course("Java Fundamentals","08/31/22","09/01/22","In-Progress");
        repo.insertCourse(course);
    }
}