package com.example.android.mygreatuniversity.UI;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class CourseView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Assign the xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set up the back is up when children are present.
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Populate the data for the Recycler view
        //Get the id
        RecyclerView recyclerView = findViewById(R.id.courseListRecyclerView);
        Repo repo = new Repo(getApplication());
        List<Course> courses = repo.getCourses();
        Log.d("adapter", "current value of courses in courseVied" + courses);
        //Set the adapter and layoutManger
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Use the Adapter to display the course list
        courseAdapter.setCourses(courses);
    }
}