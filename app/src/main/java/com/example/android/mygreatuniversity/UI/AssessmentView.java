package com.example.android.mygreatuniversity.UI;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class AssessmentView extends AppCompatActivity {
    //TODO create this class and make sure to modify the different Id's from Course view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Assign the xml foe the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_view);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set up the back is up when children are present.
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Populate the CourseList for the Recycler view
        RecyclerView recyclerView = findViewById(R.id.assessmentListRecyclerView);
        Repo repo = new Repo(getApplication());
        List<Assessment> assessments = repo.getAssessments();
        //Set the CourseAdapter and LayoutManger
        //TODO going to have to create a new assessment adapter
//        final CourseAdapter courseAdapter = new CourseAdapter(this);
//        recyclerView.setAdapter(courseAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        //Set The Assessments Via the adapter
//        courseAdapter.setMentors(assessments);
    }
}
