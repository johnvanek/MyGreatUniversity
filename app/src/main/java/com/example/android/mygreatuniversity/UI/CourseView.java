package com.example.android.mygreatuniversity.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class CourseView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Assign the xml for the view
        // New Token Test Commit
        // Test for the new auth token is now in use
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set up the back is up when children are present.
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Populate the CourseList for the Recycler view
        RecyclerView recyclerView = findViewById(R.id.mentorListRecyclerView);
        Repo repo = new Repo(getApplication());
        List<Course> courses = repo.getCourses();
        List<Mentor> mentors = repo.getMentors();
        //Set the CourseAdapter and LayoutManger
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Set The Courses Via the adapter
        courseAdapter.setCourses(courses);
        courseAdapter.setMentors(mentors);
    }

    //Inflate the menu
    //This is what shows the menu to the top right on the toolbar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_menu, menu);
        return true;
    }

    public void goToCourseCreate(MenuItem item) {
        Intent intent = new Intent(CourseView.this, CourseCreateView.class);
        startActivity(intent);
    }
}