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
    Repo repo = new Repo(getApplication());
    RecyclerView recyclerView = findViewById(R.id.facultyRecycler);
    EditText searchBar = findViewById(R.id.searchBar);
    String query;

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
        //Some sort of problem on load probably because
    }

    public void performSearch(View view) {
        //TODO new to tie this to the button onClick
        // This will need to call one of two methods depending on the type of box selected.
        //techFaculty = repo.getAllTechSupport();
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
}
