package com.example.android.mygreatuniversity.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CourseViewDetailed extends AppCompatActivity {
    final Calendar CalenderStart = Calendar.getInstance();
    final Calendar CalenderEnd = Calendar.getInstance();
    //Declarations for the fields
    EditText courseTitle;
    EditText startText, endText;
    EditText courseStatus;
    //intent data references
    String title;
    int courseId;
    Repo repo;
    //Date References & Declarations
    String format = "MM/dd/yy";
    DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_detailed);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar actionBar = getSupportActionBar();

        //Set up the back is up when children are present.
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);   //show back button
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Link the Declared textViews from xml file.
        courseTitle = findViewById(R.id.editTextTitle);
        courseId = getIntent().getIntExtra("id", -1);
        //get and assign the intent data
        title = getIntent().getStringExtra("title");

        //Assign the textViews the intent data
        courseTitle.setText(title);

        //DatePicker-Related-Logic
        //Get the xml id's for the edit text fields
        startText = findViewById(R.id.editTextStartDate);
        endText = findViewById(R.id.editTextEndDate);

        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                CalenderStart.set(Calendar.YEAR, year);
                CalenderStart.set(Calendar.MONTH, month);
                CalenderStart.set(Calendar.DAY_OF_MONTH, day);
                updateStartDateLabel();
            }
        };

        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                CalenderEnd.set(Calendar.YEAR, year);
                CalenderEnd.set(Calendar.MONTH, month);
                CalenderEnd.set(Calendar.DAY_OF_MONTH, day);
                updateEndDateLabel();
            }
        };

        //Set the listener on the start edit Text
        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseViewDetailed.this,
                        startDatePicker, CalenderStart.get(Calendar.YEAR),
                        CalenderStart.get(Calendar.MONTH),
                        CalenderStart.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        endText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseViewDetailed.this,
                        endDatePicker, CalenderEnd.get(Calendar.YEAR),
                        CalenderEnd.get(Calendar.MONTH),
                        CalenderEnd.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
    }

    private void updateStartDateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        startText.setText(simpleDateFormat.format(CalenderStart.getTime()));
    }

    private void updateEndDateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        endText.setText(simpleDateFormat.format(CalenderEnd.getTime()));
    }

    public void saveButton(View view) {
        //This is 12:50 part 3 by bike shop.
        //Create a new Course
        //TODO create a method here to check if these fields are null or empty strings.
        //Need a method here to check if these fields are null or not.
        Course editedCourse = new Course(
                courseTitle.getText().toString(),
                startText.getText().toString(),
                endText.getText().toString(),
                courseStatus.getText().toString());
        // Then update
        repo.update(editedCourse);
    }
}



