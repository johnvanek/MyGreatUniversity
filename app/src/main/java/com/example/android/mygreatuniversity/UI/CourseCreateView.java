package com.example.android.mygreatuniversity.UI;
// TODO this create view should be similar to how create term is handled
//  Because of how repo works in reassigning these these recycler view values
//  When the user is creating an items they shouldn't have the ability alter data in
//  the repo until created.

import static com.example.android.mygreatuniversity.Utils.Utils.courseStatusPosition;
import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseCreateView extends AppCompatActivity {
    //TODO insert these fields but make sure to clean up the code referencing assessments
    // And the recycler view might throw a runtime error. When it is attempted to be created.
    //**************  START DECLARATIONS *********************
    final Calendar CalenderStart = Calendar.getInstance();
    final Calendar CalenderEnd = Calendar.getInstance();

    boolean arrivedFromIntent = true;
    //Field Declarations
    EditText courseTitle;
    EditText startText, endText, courseNoteEditText;
    TextView mentorTextView, noteCardLabel, courseLabel;
    Spinner courseStatus;
    Spinner mentorSpinner;
    Spinner assessmentSpinner;
    AssessmentSpinnerAdapter assessmentSpinnerAdapter;
    RecyclerView courseAssessmentRecyclerView;
    MentorSpinnerAdapter mentorSpinnerAdapter;
    CardView mentorCard, noteCard;
    Button sendButton;
    int amtSelected = 0;
    boolean firstRender = true;
    boolean isSarahSelected = true;
    ConstraintLayout courseLayout, courseNoteLayout, generalLayout;
    // Mentor fields
    EditText mentorName, mentorPhone, mentorEmail;
    //intent data references
    String intentTitle, intentStartDate, intentEndDate, intentStatus;
    //mentor intent strings
    String mentorIntentName, mentorIntentPhone, mentorIntentEmail;

    int intentTermId;
    int intentCourseId, intentMentorId;
    Repo repo = new Repo(getApplication());
    List<Assessment> courseAssessments;
    //Date References & Declarations
    String format = "MM/dd/yy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
    DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    //****************** END DECLARATIONS **************************************
    //Start of the OnCreate Logic
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Stops the soft-keyboard from acting so erratically.
        hideKeyboard(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        //***************** DISPLAY LOGIC *************
        //Sets the activity
        super.onCreate(savedInstanceState);
        //This tell android what xml file to use for the layout
        //TODO test how this layout if functioning
        setContentView(R.layout.activity_course_create);
        //Assigns the toolbar from xml
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar actionBar = getSupportActionBar();

        //Set nav icon location as back if child
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);   //show back button
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //XML FIELD Assignments

        //Course Assignments
        courseTitle = findViewById(R.id.editTextName);
        startText = findViewById(R.id.editTextPhone);
        endText = findViewById(R.id.editTextEmail);
        courseStatus = findViewById(R.id.spinnerStatus);
        courseLayout = findViewById(R.id.LayoutCourse);
        courseLabel = findViewById(R.id.course_label);
        //Mentor Assignments

        mentorName = findViewById(R.id.mentorNameText);
        mentorPhone = findViewById(R.id.mentorPhoneText);
        mentorEmail = findViewById(R.id.mentorEmailText);
        mentorCard = findViewById(R.id.courseViewMentorCard);
        mentorTextView = findViewById(R.id.mentorLabel);

        //Note Assignments
        noteCard = findViewById(R.id.courseNoteCard);
        courseNoteLayout = findViewById(R.id.cardNoteLayout);
        noteCardLabel = findViewById(R.id.notesLabel);
        courseNoteEditText = findViewById(R.id.courseNotesEditText);
        generalLayout = findViewById(R.id.generalLayout);

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
        //Hiding for the note card ime action done does not work unless it is set to a single-line.

        //Set it so that when it is focused it looks correct
        courseLabel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                courseTitle.clearFocus();
                courseTitle.setCursorVisible(false);
                hideKeyboard(CourseCreateView.this);
                return false;
            }
        });

        courseNoteEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseCreateView.this);
                courseNoteEditText.requestFocus();
                courseNoteEditText.setCursorVisible(true);
                return false;
            }
        });

        noteCardLabel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseCreateView.this);
                courseNoteEditText.clearFocus();
                courseNoteEditText.setCursorVisible(false);
                return false;
            }
        });

        noteCardLabel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseCreateView.this);
                courseNoteEditText.clearFocus();
                courseNoteEditText.setCursorVisible(false);
                return false;
            }
        });

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
            if (!hasFocus) {
                courseTitle.clearFocus();
                courseTitle.setCursorVisible(false);
            }
            hideKeyboard(CourseCreateView.this);
        });

        startText.setOnClickListener(view -> {
            //Hide the flickering coming from the title
            courseTitle.clearFocus();
            courseTitle.setCursorVisible(false);

            hideKeyboard(CourseCreateView.this);
            new DatePickerDialog(CourseCreateView.this,
                    startDatePicker, CalenderStart.get(Calendar.YEAR),
                    CalenderStart.get(Calendar.MONTH),
                    CalenderStart.get(Calendar.DAY_OF_MONTH))
                    .show();
            startText.requestFocus();
        });

        courseLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseCreateView.this);
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
            hideKeyboard(CourseCreateView.this);
            new DatePickerDialog(CourseCreateView.this,
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
                hideKeyboard(CourseCreateView.this);
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
                v.performClick();
                hideKeyboard(CourseCreateView.this);
                courseTitle.setCursorVisible(false);
                courseTitle.clearFocus();
                return false;
            }
        });
        //*******************************MENTOR SPINNER*****************************************//
        //SPINNER POPULATION FROM ROOM DATABASE
        mentorSpinner = findViewById(R.id.mentorSpinner);
        //Set it to not selected the default behavior is to select the// first item
        mentorSpinner.setSelection(Adapter.NO_SELECTION, false);
        //Part of the Keyboard hiding logic
        mentorSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                hideKeyboard(CourseCreateView.this);
                courseTitle.setCursorVisible(false);
                courseTitle.clearFocus();
                return false;
            }
        });

        //This is a list of mentors from the room database
        List<Mentor> mentorList = repo.getMentors();
        //This converts the list from Mentors to an array to be used by the mentor spinner adapter.
        Mentor[] mentorArray = mentorList.toArray(new Mentor[0]);
        mentorSpinnerAdapter = new MentorSpinnerAdapter(CourseCreateView.this,
                R.layout.mentor_spinner_item,
                mentorArray);
        mentorSpinnerAdapter.setDropDownViewResource(R.layout.mentor_spinner_item);
        //mentorSpinner.setSelection(intentMentorId - 1);
        //set the adapter
        mentorSpinner.setAdapter(mentorSpinnerAdapter);
        mentorSpinner.setSelection(Adapter.NO_SELECTION, false);
        mentorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //So the first item in a Spinner is selected by default meaning that if the user
            // Clicks this first item again nothing will register as selected
            // TODO leave this as it but write it up for the instructor.
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                Mentor currentSelectedMentor = (Mentor) mentorSpinner.getSelectedItem();

                    mentorName.setText(currentSelectedMentor.getName());
                    mentorPhone.setText(currentSelectedMentor.getPhoneNumber());
                    mentorEmail.setText(currentSelectedMentor.getEmail());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                //The ui for this is just going to be a little buggy
            }
        });
    } //This is the end of the onCreate Methods and data initialization

    //******************************Utility Methods*******************************************
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
                -1,
                courseNoteEditText.getText().toString()
        );

        //The primary key is auto-incremented in the database
        repo.insertCourse(editedCourse);
        Intent intent = new Intent(
                    CourseCreateView.this,
                    CourseView.class);

        //Change back to the intended destination. And let the user know a state change has occurred.
        Toast.makeText(getApplicationContext(), "New Course Created", Toast.LENGTH_SHORT).show();
        //Either send the use back to the main screen or back to course view
        startActivity(intent);
    }

    public void deleteState(View view) {
        //Declare the intent
        Intent intent  = new Intent(
                    CourseCreateView.this,
                    CourseView.class);

        Toast.makeText(getApplicationContext(), "Course Discarded", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }















































    }
