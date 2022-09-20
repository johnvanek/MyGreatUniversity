package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.courseStatusPosition;
import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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
    //TODO Add the mentor information on display to be shown.

    //**************  START DECLARATIONS *********************
    final Calendar CalenderStart = Calendar.getInstance();
    final Calendar CalenderEnd = Calendar.getInstance();
    //Declarations for the fields
    EditText courseTitle;
    EditText startText, endText;
    Spinner courseStatus;
    //intent data references
    String intentTitle, intentStartDate, intentEndDate, intentStatus;
    int intentCourseId;
    Repo repo = new Repo(getApplication());

    //Date References & Declarations
    String format = "MM/dd/yy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
    DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    //****************** END DECLARATIONS **************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideKeyboard(this);
        //***************** DISPLAY LOGIC *************
        //Sets the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_detailed);
        //Assigns the toolbar from xml
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar actionBar = getSupportActionBar();

        //Set nav icon location as back if child
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);   //show back button
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //************ INTENT DATA PASSING ****************
        //XML FIELD DECLARATIONS
        courseTitle = findViewById(R.id.editTextTitle);
        startText = findViewById(R.id.editTextStartDate);
        endText = findViewById(R.id.editTextEndDate);
        courseStatus = findViewById(R.id.spinnerStatus);
        //Get and assign the intent data to local variables
        intentCourseId = getIntent().getIntExtra("id", -1);
        intentTitle = getIntent().getStringExtra("title");
        intentStartDate = getIntent().getStringExtra("startDate");
        intentEndDate = getIntent().getStringExtra("endDate");
        intentStatus = getIntent().getStringExtra("status");
        //Assign the XML Fields the values from the intents or that have been edited
        courseTitle.setText(intentTitle);
        courseStatus.setSelection(courseStatusPosition(this, intentStatus));
        startText.setText(intentStartDate);
        endText.setText(intentEndDate);
        //************************ DATEPICKER LOGIC START & END ********************
        //Get the xml id's for the edit text fields representing start and end edit text


        //Set listeners on both of the Start and End dialogs that will be popping up for the
        // condition of if the Dialog date is modified or assigned.

        startDatePicker = (view, year, month, day) -> {
            CalenderStart.set(Calendar.YEAR, year);
            CalenderStart.set(Calendar.MONTH, month);
            CalenderStart.set(Calendar.DAY_OF_MONTH, day);
            updateStartDateEditTextField();
        };

        endDatePicker = (view, year, month, day) -> {
            CalenderEnd.set(Calendar.YEAR, year);
            CalenderEnd.set(Calendar.MONTH, month);
            CalenderEnd.set(Calendar.DAY_OF_MONTH, day);
            updateEndDateEditTextField();
        };

        courseTitle.setOnClickListener(view -> {
            courseTitle.requestFocus();
            courseTitle.setCursorVisible(true);
        });

        //Set onClick Listeners for both the Start and End XML EditTexts -> that creates the new
        //DatePicker Dialog's mentioned above and show them.
        startText.setOnClickListener(view -> {
            //Hide the flickering coming from the title

            courseTitle.setCursorVisible(false);

            hideKeyboard(CourseViewDetailed.this);
            new DatePickerDialog(CourseViewDetailed.this,
                    startDatePicker, CalenderStart.get(Calendar.YEAR),
                    CalenderStart.get(Calendar.MONTH),
                    CalenderStart.get(Calendar.DAY_OF_MONTH))
                    .show();
            courseTitle.clearFocus();
            startText.requestFocus();
        });

        endText.setOnClickListener(view -> {
            //Hide the flickering coming from the title
            courseTitle.setCursorVisible(false);
            // And get the current focus
            courseTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            hideKeyboard(CourseViewDetailed.this);
            new DatePickerDialog(CourseViewDetailed.this,
                    endDatePicker, CalenderEnd.get(Calendar.YEAR),
                    CalenderEnd.get(Calendar.MONTH),
                    CalenderEnd.get(Calendar.DAY_OF_MONTH))
                    .show();
            endText.requestFocus();
        });

        //************************ KEYBOARD HIDING LOGIC SOFT KEYBOARD ********************
        startText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                hideKeyboard(this);
            }
        });

        endText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                hideKeyboard(this);
            }
        });
    }

    private void updateStartDateEditTextField() {
        hideKeyboard(this);
        startText.setText(dateFormat.format(CalenderStart.getTime()));
    }

    private void updateEndDateEditTextField() {
        hideKeyboard(this);
        endText.setText(dateFormat.format(CalenderEnd.getTime()));
    }

    public void saveState(View view) {
        Course editedCourse = new Course(
                courseTitle.getText().toString(),
                startText.getText().toString(),
                endText.getText().toString(),
                courseStatus.getSelectedItem().toString());

        //Convenience methods work by comparing the primary key
        editedCourse.setCourseID(intentCourseId); // This will set it to Id that was passed
        repo.updateCourse(editedCourse);
        //After done performing the update return to the the Course View
        Intent intent = new Intent(
                CourseViewDetailed.this,
                CourseView.class);
        startActivity(intent);
    }

    public void deleteState(View view) {
        Course editedCourse = new Course(
                courseTitle.getText().toString(),
                startText.getText().toString(),
                endText.getText().toString(),
                courseStatus.getSelectedItem().toString());

        editedCourse.setCourseID(intentCourseId);
        repo.deleteCourse(editedCourse);
        Intent intent = new Intent(
                CourseViewDetailed.this,
                CourseView.class);
        startActivity(intent);
    }
}



