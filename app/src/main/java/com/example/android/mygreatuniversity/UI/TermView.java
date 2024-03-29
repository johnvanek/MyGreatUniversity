package com.example.android.mygreatuniversity.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
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
    //Commit test Delete this after confirming that this works from a mac.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Assign the xml for the view
        // Token test
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
        //test
    }

    //Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.term_menu, menu);
        return true;
    }

    public void goToTermCreate(MenuItem item) {
        Intent intent = new Intent(TermView.this, TermCreate.class);
        startActivity(intent);
    }
}