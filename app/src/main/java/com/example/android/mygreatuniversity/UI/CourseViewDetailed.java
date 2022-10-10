package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.courseStatusPosition;
import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseViewDetailed extends AppCompatActivity {
    //**************  START DECLARATIONS *********************
    final Calendar CalenderStart = Calendar.getInstance();
    final Calendar CalenderEnd = Calendar.getInstance();

    boolean arrivedFromIntent = true;
    int itemSpinnerChangeCounter = 0;
    //Declarations for the fields
    EditText courseTitle;
    EditText startText, endText;
    TextView mentorTextView;
    Spinner courseStatus;
    Spinner mentorSpinner;
    MentorSpinnerAdapter mentorSpinnerAdapter;
    CardView mentorCard;

    ConstraintLayout courseLayout;
    // Mentor fields
    EditText mentorName, mentorPhone, mentorEmail;
    //intent data references
    String intentTitle, intentStartDate, intentEndDate, intentStatus;
    //mentor intent strings
    String mentorIntentName, mentorIntentPhone, mentorIntentEmail;
    int intentTermId;
    int intentCourseId, intentMentorId;
    Repo repo = new Repo(getApplication());
    //Date References & Declarations
    String format = "MM/dd/yy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
    DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    //****************** END DECLARATIONS **************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideKeyboard(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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
        mentorCard = findViewById(R.id.courseViewMentorCard);
        mentorTextView = findViewById(R.id.mentorLabel);

        //Get and assign the intent data to local variables
        intentCourseId = getIntent().getIntExtra("id", -1);
        intentTitle = getIntent().getStringExtra("title");
        intentStartDate = getIntent().getStringExtra("startDate");
        intentEndDate = getIntent().getStringExtra("endDate");
        intentStatus = getIntent().getStringExtra("status");
        //Mentor intent data
        intentMentorId = getIntent().getIntExtra("mentorId", -1);
        mentorIntentName = getIntent().getStringExtra("mentorName");
        mentorIntentPhone = getIntent().getStringExtra("mentorPhone");
        mentorIntentEmail = getIntent().getStringExtra("mentorEmail");
        //Term intent data
        intentTermId = getIntent().getIntExtra("termID", -1);
        //Assign the XML Fields the values from the intents or that have been edited
        courseTitle.setText(intentTitle);
        //If this does not match one of the spinner values it is set to In-Progress
        courseStatus.setSelection(courseStatusPosition(this, intentStatus));
        startText.setText(intentStartDate);
        endText.setText(intentEndDate);
        //Set the XML fields for the mentor section
        mentorName.setText(mentorIntentName);
        mentorEmail.setText(mentorIntentEmail);
        mentorPhone.setText(mentorIntentPhone);
        //************************* DATEPICKER LOGIC START & END ************************

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

        //************ KEYBOARD HIDING ****************
        //Known Issues ****
        //There is a slight UI problem if the user continues to scroll to the bottom of the Mentor view
        //And tries to click there as the title will still be in focus cant resolve.

        courseTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                courseTitle.requestFocus();
                courseTitle.setCursorVisible(true);
                return false;
            }
        });

        courseTitle.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                courseTitle.clearFocus();
                courseTitle.setCursorVisible(false);
                hideKeyboard(this);
            }
            return false;
        });

        //I do not think that this actually works
        courseTitle.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                courseTitle.clearFocus();
                courseTitle.setCursorVisible(false);
            }
            hideKeyboard(CourseViewDetailed.this);
        });

        startText.setOnClickListener(view -> {
            //Hide the flickering coming from the title
            courseTitle.clearFocus();
            courseTitle.setCursorVisible(false);

            hideKeyboard(CourseViewDetailed.this);
            new DatePickerDialog(CourseViewDetailed.this,
                    startDatePicker, CalenderStart.get(Calendar.YEAR),
                    CalenderStart.get(Calendar.MONTH),
                    CalenderStart.get(Calendar.DAY_OF_MONTH))
                    .show();
            startText.requestFocus();
        });

        courseLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseViewDetailed.this);
                courseTitle.clearFocus();
                courseTitle.setCursorVisible(false);
                return false;
            }
        });

        endText.setOnClickListener(view -> {
            //Hide the flickering coming from the title
            courseTitle.clearFocus();
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

        myToolbar.setOnClickListener(v -> {
            hideKeyboard(this);
            courseTitle.clearFocus();
        });

        courseLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseViewDetailed.this);
                courseTitle.setCursorVisible(false);
                courseTitle.clearFocus();
                return false;
            }
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

        courseStatus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseViewDetailed.this);
                courseTitle.setCursorVisible(false);
                courseTitle.clearFocus();
                return false;
            }
        });

        //SPINNER POPULATION FROM ROOM DATABASE
        mentorSpinner = findViewById(R.id.mentorSpinner);
        //Part of the Keyboard hiding logic
        mentorSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseViewDetailed.this);
                courseTitle.setCursorVisible(false);
                courseTitle.clearFocus();
                return false;
            }
        });

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
        //This is okay because we are still in the onCreate
        //The primary keys auto increment starting from one
        //mentorSpinner.setSelection(intentMentorId - 1);
        //set the adapter
        mentorSpinner.setAdapter(mentorSpinnerAdapter);
        mentorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //Token test
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // This gets the current mentor that is selected by returning the spinner position.
                // The position here has defaulted to zero from the override
                Mentor mentor = null;
                if (arrivedFromIntent) {
                    Mentor[] mentorValues = mentorSpinnerAdapter.returnValuesAsArray();
                    //DO something here
                    int intentMentorPosition = 0;
                    for (Mentor spinnerMentor: mentorValues) {
                        if(spinnerMentor.getMentorID() == intentMentorId) {
                           intentMentorPosition = mentorSpinnerAdapter.getPosition(spinnerMentor);
                           mentor = spinnerMentor;
                        }
                    }
                    mentorSpinner.setSelection(intentMentorPosition);
                    //TODO disable this when done this just for testing
                    Toast.makeText(CourseViewDetailed.this, "ID: " + mentor.getMentorID()
                                    + "\nName: " + mentor.getName(),
                            Toast.LENGTH_SHORT).show();
                    arrivedFromIntent = false;
                } else if (itemSpinnerChangeCounter >= 1) {
                    mentor = mentorSpinnerAdapter.getItem(position);
                    // for test purposes I am going to make this a toast.
                    Toast.makeText(CourseViewDetailed.this, "ID: " + mentor.getMentorID()
                                    + "\nName: " + mentor.getName(),
                            Toast.LENGTH_SHORT).show();
                }
                itemSpinnerChangeCounter++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
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
        Mentor selectedMentor = (Mentor) mentorSpinner.getSelectedItem();
        Course editedCourse = new Course(
                courseTitle.getText().toString(),
                startText.getText().toString(),
                endText.getText().toString(),
                courseStatus.getSelectedItem().toString(),
                selectedMentor.getMentorID(),
                intentTermId
        );

        //The primary key is auto-incremented in the database
        editedCourse.setCourseID(intentCourseId); // This will set it to Id that was passed
        repo.updateCourse(editedCourse);

        Intent intent;
        if(StateManager.SelectedTerm.getArrivedToCourseFromTermView()) {
            intent = new Intent(
                    CourseViewDetailed.this,
                    TermViewDetailed.class);
        } else {
            //Must have come from the course Activity Then
            intent = new Intent(
                    CourseViewDetailed.this,
                    CourseView.class);
        }

        startActivity(intent);
    }
//TODO test that this works
    public void deleteState(View view) {
        Mentor selectedMentor = (Mentor) mentorSpinner.getSelectedItem();
        //Delete the associated course
        Course editedCourse = new Course(
                courseTitle.getText().toString(),
                startText.getText().toString(),
                endText.getText().toString(),
                courseStatus.getSelectedItem().toString(),
                selectedMentor.getMentorID(),
                intentTermId
        );
        editedCourse.setCourseID(intentCourseId);
        repo.deleteCourse(editedCourse);

        //Declare the intent
        Intent intent;
        if(StateManager.SelectedTerm.getArrivedToCourseFromTermView()) {
            intent = new Intent(
                    CourseViewDetailed.this,
                    TermViewDetailed.class);
        } else {
            //Must have come from the course Activity Then
            intent = new Intent(
                    CourseViewDetailed.this,
                    CourseView.class);
        }
        startActivity(intent);
    }
}



