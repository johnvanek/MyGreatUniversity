package com.example.android.mygreatuniversity.UI;

import android.app.Activity;
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
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Set The Assessments Via the adapter
        assessmentAdapter.setAssessments(assessments);
    }

    //Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.assessment_menu, menu);
        return true;
    }

    public void goToAssessmentCreate(MenuItem item) {
        //TODO should go to a new activity very similar to the screen that is used for the editing.
        // Will have to create all new activities for those screens as well.

        //Intent intent = new Intent(MainActivity.this, TermView.class);
        //startActivity(intent);
    }
}
