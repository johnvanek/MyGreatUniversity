package com.example.android.mygreatuniversity.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.TechSupport;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class FacultySearch extends AppCompatActivity {
    //Declarations
    List<TechSupport> techFaculty;
    Repo repo;
    RecyclerView recyclerView;
    EditText searchBar;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_search);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set the other fields on the page
        repo = new Repo(getApplication());
        //Try to get the Id's for the fields throws error if wrong id's are used but does not flag
        // At compile time only runtime makes debugging difficult.
        try {
            recyclerView = findViewById(R.id.facultyRecycler);
            searchBar = findViewById(R.id.searchTextBar);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Attempted to get values for fields : " + e.getMessage());
        }
        //Set up the back is up when children are present.
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        //Just sets initial data to blank so that the Ui wrapped to content does not collapse
        initialDataDisplay();
        //TODO set a Query to get some results here so that the table is never empty as that looks wrong
    }

    public void performSearch(View view) {
        //TODO new to tie this to the button onClick
        // This will need to call one of two methods depending on the type of box selected.

        //Define the data you are displaying
        //Get the value of the editText Text content
        query = String.valueOf(searchBar.getText());
        techFaculty = repo.facultyTechQuery(query);

        //Set the Adapter and Layout Manager for the for the recycler view
        final TechAdapter facultyTechAdapter = new TechAdapter(this);
        recyclerView.setAdapter(facultyTechAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Set the value of the Recycler view to the data of the query
        facultyTechAdapter.setTechs(techFaculty);
    }

    private void initialDataDisplay() {
        //Define the data you are displaying
        //Get the value of the editText Text content
        query = ""; //An empty string this should populate just empty content
        techFaculty = repo.facultyTechQuery(query);

        //Set the Adapter and Layout Manager for the for the recycler view
        final TechAdapter facultyTechAdapter = new TechAdapter(this);
        recyclerView.setAdapter(facultyTechAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Set the value of the Recycler view to the data of the query
        facultyTechAdapter.setTechs(techFaculty);
    }
}
