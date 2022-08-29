package com.example.android.mygreatuniversity.UI;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.R;

public class CourseViewDetailed extends AppCompatActivity {

    //Declarations for the fields
    EditText courseTitle;

    //Page Reference for the intent data
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_detailed);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set up the back is up when children are present.
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Link the Declared textViews from xml file.
        courseTitle = findViewById(R.id.editTextSelectedCourseTitle);

        //get and assign the intent data
        title = getIntent().getStringExtra("title");

        //Assign the textViews the intent data
        courseTitle.setText(title);
    }
}