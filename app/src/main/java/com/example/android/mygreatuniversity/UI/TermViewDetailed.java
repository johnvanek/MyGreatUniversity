package com.example.android.mygreatuniversity.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;

import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class TermViewDetailed extends AppCompatActivity {
    //**************  FIELDS *********************
    //Intent Data
    String intentTitle, intentStart, intentEnd;
    int intentTermID;
    //XML Fields
    EditText termTitle, termStart, termEnd;
    //Repo Access
    Repo repo = new Repo(getApplication());

    //TODO Make this edit Text functionality with the dates similar to to dates from Course View.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Assign the xml for the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view_detailed);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set up the back is up when children are present.
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        //
        //TODO get the intent data passed from term view and show it
        //************ INTENT DATA PASSING ****************
        //Define the fields
        termTitle = findViewById(R.id.termTitle);
        //Assign the Intent Data
        intentTitle = getIntent().getStringExtra("title");
        //This will crash if not valid id
        intentTermID = getIntent().getIntExtra("id", -1);
        // Set fields to the Intent Data
        termTitle.setText(intentTitle);
        //Populate the Term List for the Recycler view
        RecyclerView recyclerView = findViewById(R.id.selectedTermRecyler);
        //Using the method in Courses query which courses belong to this term
        List<Course> termCourses = repo.getTermCourses(intentTermID);
        // Set the TermAdapter and LayoutManger
        final TermCourseAdapter termCourseAdapter = new TermCourseAdapter(this);
        recyclerView.setAdapter(termCourseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Set The TermsCourses Via the adapter
        termCourseAdapter.setTermCourses(termCourses);
    }
}
