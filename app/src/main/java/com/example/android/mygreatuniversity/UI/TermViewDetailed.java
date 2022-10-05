package com.example.android.mygreatuniversity.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;
import com.example.android.mygreatuniversity.Utils.Utils;

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
        //This gets called from the back button meaning that OnDestroy must be being called

        Context context = getApplicationContext();
        CharSequence text = "Hello From the OnCreate LifeCycle";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        //Assign the xml for the view
        //TODO Rework this perhaps override the OnCreate or OnDestroy method to save the state
        // Perhaps in a utility class called state manager or something.

        //This line right here restores from the saved instance state if this activity were
        // to de destroyed.
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
        //TODO get the intent data passed because were are not passing intent data back from
        // The child all of these intent values intialize to null
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

        //TODO the better way to do this is to use built in lifecycle callback methods
        //Save the state into stateManager if they go back
        StateManager.SelectedTermState.setTermID(intentTermID);
        StateManager.SelectedTermState.setTermTitle(intentTitle);

        List<Course> termCourses = repo.getTermCourses(intentTermID);
        // Set the TermAdapter and LayoutManger
        final TermCourseAdapter termCourseAdapter = new TermCourseAdapter(this);
        recyclerView.setAdapter(termCourseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Set The TermsCourses Via the adapter
        //Get the list of mentors from the repository
        List<Mentor> mentors = repo.getMentors();
        //Set the data for the adapters
        termCourseAdapter.setTermCourses(termCourses);
        termCourseAdapter.setMentors(mentors);
    }

    @Override
    public void onResume() {
        //This method never gets called
        super.onResume();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        Context context = getApplicationContext();
        CharSequence text = "Hello From the OnResume LifeCycle";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onDestroy() {
        //For some reason on going back android detroyes then recreates this scene perhaps there
        // is a user for stateManager after all.
        super.onDestroy();
        Context context = getApplicationContext();
        CharSequence text = "Hello From the OnDestroy LifeCycle";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
