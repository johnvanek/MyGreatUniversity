package com.example.android.mygreatuniversity.UI;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;

import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view_detailed);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        //Set up the back is up when children are present.
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Data Values
        List<Course> termCourses;

        //************ XML Fields ****************
        termTitle = findViewById(R.id.termTitle);

        if(StateManager.SelectedTerm.getHasSavedData()) {
            //************ Data Recovery From StateManager ****************
            //Set the termTitle
            termTitle.setText(StateManager.SelectedTerm.getTermTitle());
            //Get the term Id from the saved state and then call from the repo
            termCourses = repo.getTermCourses(StateManager.SelectedTerm.getTermID());
            //Set state back to false
            StateManager.SelectedTerm.setHasSavedData(false);
        } else {
            //************ INTENT DATA PASSING ****************
            //Assign the Intent Data
            intentTitle = getIntent().getStringExtra("title");
            //This will crash if not valid id
            intentTermID = getIntent().getIntExtra("id", -1);
            // Set fields to the Intent Data
            termTitle.setText(intentTitle);
            // Get the term Id from the Intent and pass that to the repo
            termCourses = repo.getTermCourses(intentTermID);
        }
        //Populate the Term List for the Recycler view
        RecyclerView recyclerView = findViewById(R.id.selectedTermRecyler);
        // Set the TermAdapter and LayoutManger
        final TermCourseAdapter termCourseAdapter = new TermCourseAdapter(this);
        recyclerView.setAdapter(termCourseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Get the list of mentors from the repository
        List<Mentor> mentors = repo.getMentors();
        //Set the data for the adapters
        termCourseAdapter.setTermCourses(termCourses);
        termCourseAdapter.setMentors(mentors);
    }

    @Override
    public void onDestroy() {
        //TODO add the rest of the fields to be stored
        // Start Date
        // End Date
        // Modify the onSave and Delete functionality for this.

        //For some reason on going back android destroys then recreates this activity on back
        //Save the state into stateManager if the user goes back from the selected course
        StateManager.SelectedTerm.setTermID(intentTermID);
        StateManager.SelectedTerm.setTermTitle(termTitle.getText().toString());

        //We now have saved some data
        StateManager.SelectedTerm.setHasSavedData(true);
        super.onDestroy();
    }
}
