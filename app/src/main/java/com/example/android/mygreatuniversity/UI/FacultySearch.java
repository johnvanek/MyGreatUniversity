package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.MentorAide;
import com.example.android.mygreatuniversity.Entity.TechSupport;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class FacultySearch extends AppCompatActivity {
    //Declarations
    List<TechSupport> techFaculty;
    List<MentorAide> aideFaculty;
    Repo repo;
    RecyclerView recyclerView;
    EditText searchBar;
    Spinner searchBySpinner, dataTypeSpinner;
    String query;

    Button searchButton;

    CardView cardView;

    @SuppressLint("ClickableViewAccessibility")
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
        try {
            // Try to get the Id's for the fields
            recyclerView = findViewById(R.id.facultyRecycler);
            //This sometimes throws class not found exception which prevents loading page.
            searchBar = findViewById(R.id.searchTextBar);
            searchBySpinner = findViewById(R.id.spinnerFacultySearch);
            dataTypeSpinner = findViewById(R.id.spinnerFacultyType);
            cardView = findViewById(R.id.cardView3);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Attempted to get values for fields : " + e.getMessage());
        }
        //Set up the back is up when children are present.
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        //Populate base data
        initData("Robot! Beep Beep");
    }

    public void performSearch(View view) {
        if (searchBySpinner.getSelectedItem().toString().equals("Name") &&
                dataTypeSpinner.getSelectedItem().toString().equals("TechSupport")) {
            //This is the case for Name & TechSupport sorted by ASC
            query = String.valueOf(searchBar.getText());
            techFaculty = repo.facultyTechNameQuery(query);

            //Set the Adapter and Layout Manager for the for the recycler view
            final TechAdapter facultyTechAdapter = new TechAdapter(this);
            recyclerView.setAdapter(facultyTechAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //Set the value of the Recycler view to the data of the query
            facultyTechAdapter.setTechs(techFaculty);
        } else if (searchBySpinner.getSelectedItem().toString().equals("Availability") &&
                dataTypeSpinner.getSelectedItem().toString().equals("TechSupport")) {
            //This is the case for Availability & TechSupport sorted by ASC
            query = String.valueOf(searchBar.getText());
            techFaculty = repo.facultyTechAvailQuery(query);

            //Set the Adapter and Layout Manager for the for the recycler view
            final TechAdapter facultyTechAdapter = new TechAdapter(this);
            recyclerView.setAdapter(facultyTechAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //Set the value of the Recycler view to the data of the query
            facultyTechAdapter.setTechs(techFaculty);
        } else if (searchBySpinner.getSelectedItem().toString().equals("Name") &&
                dataTypeSpinner.getSelectedItem().toString().equals("MentorAide")) {
            //This is the case for Name & MentorAide sorted by ASC
            query = String.valueOf(searchBar.getText());
            //techFaculty = repo.facultyTechAvailQuery(query);
            aideFaculty = repo.facultyAideNameQuery(query);
            //Set the Adapter and Layout Manager for the for the recycler view
            final AideAdapter facultyAideAdapter = new AideAdapter(this);
            recyclerView.setAdapter(facultyAideAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //Set the value of the Recycler view to the data of the query
            facultyAideAdapter.setAides(aideFaculty);
        } else if (searchBySpinner.getSelectedItem().toString().equals("Availability") &&
                dataTypeSpinner.getSelectedItem().toString().equals("MentorAide")) {
            //This is the case for Availability & MentorAide
            query = String.valueOf(searchBar.getText());
            aideFaculty = repo.facultyAideAvailQuery(query);
            //Set the Adapter and Layout Manager for the recycler view
            final AideAdapter facultyAideAdapter = new AideAdapter(this);
            recyclerView.setAdapter(facultyAideAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //Set the value of the Recycler view to the data of the query
            facultyAideAdapter.setAides(aideFaculty);
        }
        hideKeyboard(FacultySearch.this);
    }

    //Just used to set the data to blank at start nothing will match on this case
    public void initData(String query) {
        techFaculty = repo.facultyTechNameQuery(query);

        //Set the Adapter and Layout Manager for the for the recycler view
        final TechAdapter facultyTechAdapter = new TechAdapter(this);
        recyclerView.setAdapter(facultyTechAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Set the value of the Recycler view to the data of the query
        facultyTechAdapter.setTechs(techFaculty);
    }

}
