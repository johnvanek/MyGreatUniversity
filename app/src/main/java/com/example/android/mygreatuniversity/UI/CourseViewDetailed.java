package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.courseStatusPosition;
import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseViewDetailed extends AppCompatActivity {
    //TODO Add the mentor information on display to be shown.

    //**************  START DECLARATIONS *********************
    final Calendar CalenderStart = Calendar.getInstance();
    final Calendar CalenderEnd = Calendar.getInstance();
    //Declarations for the fields
    //Selected Course fields
    EditText courseTitle;
    EditText startText, endText;
    Spinner courseStatus;
    Spinner mentorSpinner;
    MentorSpinnerAdapter mentorSpinnerAdapter;

    LinearLayout courseLayout;
    // Mentor fields
    EditText mentorName, mentorPhone, mentorEmail;
    //intent data references
    String intentTitle, intentStartDate, intentEndDate, intentStatus;
    //mentor intent strings
    String mentorIntentName, mentorIntentPhone, mentorIntentEmail;
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

        //Course Declarations
        courseTitle = findViewById(R.id.editTextName);
        startText = findViewById(R.id.editTextPhone);
        endText = findViewById(R.id.editTextEmail);
        courseStatus = findViewById(R.id.spinnerStatus);
        courseLayout = findViewById(R.id.LayoutCourse);
        //Mentor Declarations

        mentorName = findViewById(R.id.mentorNameText);
        mentorPhone = findViewById(R.id.mentorPhoneText);
        mentorEmail = findViewById(R.id.mentorEmailText);

        //Get and assign the intent data to local variables
        intentCourseId = getIntent().getIntExtra("id", -1);
        intentTitle = getIntent().getStringExtra("title");
        intentStartDate = getIntent().getStringExtra("startDate");
        intentEndDate = getIntent().getStringExtra("endDate");
        intentStatus = getIntent().getStringExtra("status");
        //Mentor intent data
        mentorIntentName = getIntent().getStringExtra("mentorName");
        mentorIntentPhone = getIntent().getStringExtra("mentorPhone");
        mentorIntentEmail = getIntent().getStringExtra("mentorEmail");
        //Assign the XML Fields the values from the intents or that have been edited
        courseTitle.setText(intentTitle);
        courseStatus.setSelection(courseStatusPosition(this, intentStatus));
        startText.setText(intentStartDate);
        endText.setText(intentEndDate);
        //Set the XML fields for the mentor section
        mentorName.setText(mentorIntentName);
        mentorEmail.setText(mentorIntentEmail);
        mentorPhone.setText(mentorIntentPhone);
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
        //Better way to do this is like the example in mentor
        // Keep other methods for now just to not break anything.

        myToolbar.setOnClickListener(v ->{
            hideKeyboard(this);
            courseTitle.clearFocus();
        });

        courseLayout.setOnClickListener(v -> {
            hideKeyboard(this);
            courseTitle.clearFocus();
        });

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
        //SPINNER POPULATION FROM ROOM DATABASE
        mentorSpinner = findViewById(R.id.mentorSpinner);
        //This is a list of mentors from the room database
        List<Mentor> mentorList = repo.getMentors();
        //This converts the list from Mentors to an array to be used by the mentor spinner adapter.
        Mentor[] mentorArray = mentorList.toArray(new Mentor[mentorList.size()]);
        mentorSpinnerAdapter = new MentorSpinnerAdapter(CourseViewDetailed.this,
                R.layout.mentor_spinner_item,
                mentorArray);
        //Have to set the view resource for the spinner adapter to enable the dropdown
        //Have to make the page larger or else the drop down will not fit.
        mentorSpinnerAdapter.setDropDownViewResource(R.layout.mentor_spinner_item);

        //set the adapter
        mentorSpinner.setAdapter(mentorSpinnerAdapter);
        mentorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // This gets the current mentor that is selected by returning the spinner position.
                Mentor mentor = mentorSpinnerAdapter.getItem(position);
                // for test purposes I am going to make this a toast.
                Toast.makeText(CourseViewDetailed.this, "ID: " + mentor.getMentorID() + "\nName: " + mentor.getName(),
                        Toast.LENGTH_SHORT).show();

                //TODO on selection populate the fields for the mentor below
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
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


//TODO implement save state again

//    public void saveState(View view) {
//        Course editedCourse = new Course(
//                courseTitle.getText().toString(),
//                startText.getText().toString(),
//                endText.getText().toString(),
//                courseStatus.getSelectedItem().toString());
//
//        //Convenience methods work by comparing the primary key
//        editedCourse.setCourseID(intentCourseId); // This will set it to Id that was passed
//        repo.updateCourse(editedCourse);
//        //After done performing the update return to the the Course View
//        Intent intent = new Intent(
//                CourseViewDetailed.this,
//                CourseView.class);
//        startActivity(intent);
//    }


//TODO implement deleteState again

//    public void deleteState(View view) {
//        Course editedCourse = new Course(
//                courseTitle.getText().toString(),
//                startText.getText().toString(),
//                endText.getText().toString(),
//                courseStatus.getSelectedItem().toString());
//
//        editedCourse.setCourseID(intentCourseId);
//        repo.deleteCourse(editedCourse);
//        Intent intent = new Intent(
//                CourseViewDetailed.this,
//                CourseView.class);
//        startActivity(intent);
//    }
}



