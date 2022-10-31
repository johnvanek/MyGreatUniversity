package com.example.android.mygreatuniversity.UI;

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

import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class MentorView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Assign the xml for the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_view);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set up the back is up when children are present.
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Populate the MentorList for the Recycler view
        RecyclerView recyclerView = findViewById(R.id.mentorListRecyclerView);
        Repo repo = new Repo(getApplication());
        List<Mentor> mentors = repo.getMentors();
        //Set the CourseAdapter and LayoutManger
        final MentorAdapter mentorAdapter = new MentorAdapter(this);
        recyclerView.setAdapter(mentorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Set The Courses Via the adapter
        mentorAdapter.setMentors(mentors);
    }

    //Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mentor_menu, menu);
        return true;
    }

    public void goToMentorCreate(MenuItem item) {
        //TODO should go to a new activity very similar to the screen that is used for the editing.
        // Will have to create all new activities for those screens as well.

        //Intent intent = new Intent(MainActivity.this, TermView.class);
        //startActivity(intent);
    }
}
