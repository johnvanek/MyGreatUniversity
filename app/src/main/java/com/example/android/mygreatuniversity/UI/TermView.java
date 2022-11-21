package com.example.android.mygreatuniversity.UI;

import android.content.Intent;
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
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;

import java.util.List;

public class TermView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Assign the xml for the view
        super.onCreate(savedInstanceState);

        //Set the Repo State in StateManager so that it can be accessed from adapters.
        StateManager.setApp(getApplication());
        setContentView(R.layout.activity_term_view);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set up the back is up when children are present.
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Populate the Term List for the Recycler view
        RecyclerView recyclerView = findViewById(R.id.termListRecyclerView);
        Repo repo = new Repo(getApplication());
        List<Term> terms = repo.getTerms();
        // Set the TermAdapter and LayoutManger
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Set The Terms Via the adapter
        termAdapter.setTerms(terms);
    }

    //Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.term_menu, menu);
        return true;
    }

    //TODO Add to the menu-bar of this activity a new overflow item to add terms which would take them to
    // A new screen activity etc. This functionality should be similar to The add screen overflow that is to be added.
    // This is probably the best way to do this create a menu item that go to a new create to create.
    // I would need menu-items and creation screens
    // Simplify the Creation screen where applicable
    // Course
    // Term - In Progress
    // Assessment
    // Mentor

    public void goToTermCreate(MenuItem item) {
        //TODO implement the logic here that will take the user to the term create screen
        // Need to pretty much copy and paste this logic into everything that can be created.
        // What this needs to do is take the user to very similar screen as the one for selected term.
        // And should offer the opportunity to create a crud operation.
        // Test that this is working as intended.

        Intent intent = new Intent(TermView.this, TermCreate.class);
        startActivity(intent);
    }
}