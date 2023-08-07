package com.example.android.mygreatuniversity.UI;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class FacultySearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_search);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set up the back is up when children are present.
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Populate the FacultyList for the Recycler view
        RecyclerView recyclerView = findViewById(R.id.mentorListRecyclerView);
        Repo repo = new Repo(getApplication());
        List<Course> courses = repo.getCourses();
        List<Mentor> mentors = repo.getMentors();
    }
}
