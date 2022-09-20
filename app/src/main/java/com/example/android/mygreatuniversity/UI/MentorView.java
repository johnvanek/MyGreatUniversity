package com.example.android.mygreatuniversity.UI;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.R;

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
        //TODO create the mentor list and the recycler view
        // the mentor list is a
    }
}
