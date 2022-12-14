package com.example.android.mygreatuniversity.UI;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseViewDetailed extends AppCompatActivity {
    //TODO change the layout and logic so that a plus icon is displayed under courses Assessments
    // Add an Item in the menu-bar that when clicked navigates to a course add screen.
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
    MentorSpinnerAdapter mentorSpinnerAdapter;
    CardView mentorCard,noteCard;
    Button sendButton;

    ConstraintLayout courseLayout,courseNoteLayout,generalLayout;
    // Mentor fields
    EditText mentorName, mentorPhone, mentorEmail;
    //intent data references
    String intentTitle, intentStartDate, intentEndDate, intentStatus;
    //mentor intent strings
    String mentorIntentName, mentorIntentPhone, mentorIntentEmail;

    int intentTermId;
    int intentCourseId, intentMentorId;
    Repo repo = new Repo(getApplication());
    List<Assessment>courseAssessments;
    //Date References & Declarations
    String format = "MM/dd/yy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
    DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    //****************** END DECLARATIONS **************************************
    @SuppressLint("ClickableViewAccessibility")
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
        //************ INTENT DATA PASSING ****************
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
        //Term intent data //
        intentTermId = getIntent().getIntExtra("termID", -1);
        //Assign the XML Fields the values from the intents or that have been edited
        courseTitle.setText(intentTitle);
        //If this does not match one of the spinner values it is set to In-Progress
        courseStatus.setSelection(courseStatusPosition(this, intentStatus));
        startText.setText(intentStartDate);
        endText.setText(intentEndDate);

        // Set the course notes from the repo
        courseNoteEditText.setText(repo.findCourseById(intentCourseId).getCourseNotes());
        //Set the XML fields for the mentor section
        mentorName.setText(mentorIntentName);
        mentorEmail.setText(mentorIntentEmail);
        mentorPhone.setText(mentorIntentPhone);
        //Recycler View Logic
        RecyclerView recyclerView = findViewById(R.id.courseAssessmentsRecycler);
        // Set the TermAdapter and LayoutManger
        final CourseAssessmentAdapter courseAssessmentAdapter = new CourseAssessmentAdapter(this);
        recyclerView.setAdapter(courseAssessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Get the assessments that have the same course ID
        courseAssessments = repo.getCourseAssessments(intentCourseId);
        //And they display it in the recycler view
        courseAssessmentAdapter.setCourseAssessments(courseAssessments);
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
                hideKeyboard(CourseViewDetailed.this);
                return false;
            }
        });

        courseNoteEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseViewDetailed.this);
                courseNoteEditText.requestFocus();
                courseNoteEditText.setCursorVisible(true);
                return false;
            }
        });

        noteCardLabel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseViewDetailed.this);
                courseNoteEditText.clearFocus();
                courseNoteEditText.setCursorVisible(false);
                return false;
            }
        });

        noteCardLabel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CourseViewDetailed.this);
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
                v.performClick();
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
                v.performClick();
                hideKeyboard(CourseViewDetailed.this);
                courseTitle.setCursorVisible(false);
                courseTitle.clearFocus();
                return false;
            }
        });

        //This is a list of mentors from the room database
        List<Mentor> mentorList = repo.getMentors();
        //This converts the list from Mentors to an array to be used by the mentor spinner adapter.
        Mentor[] mentorArray = mentorList.toArray(new Mentor[0]);
        mentorSpinnerAdapter = new MentorSpinnerAdapter(CourseViewDetailed.this,
                R.layout.mentor_spinner_item,
                mentorArray);
        mentorSpinnerAdapter.setDropDownViewResource(R.layout.mentor_spinner_item);
        //mentorSpinner.setSelection(intentMentorId - 1);
        //set the adapter
        mentorSpinner.setAdapter(mentorSpinnerAdapter);
        mentorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //Token test
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Note that is a a mentor is deleted that is assigned it will default to the first mentor.
                // The position here has defaulted to zero from the override
                if (arrivedFromIntent) {
                    Mentor[] mentorValues = mentorSpinnerAdapter.returnValuesAsArray();
                    //DO something here
                    int intentMentorPosition = 0;
                    for (Mentor spinnerMentor : mentorValues) {
                        if (spinnerMentor.getMentorID() == intentMentorId) {
                            //Verify that this is different
                            intentMentorPosition = mentorSpinnerAdapter.getPosition(spinnerMentor);
                            //Need to also change the first value of this to be
                            mentorSpinner.setSelected(true);
                            mentorSpinner.setSelection(intentMentorPosition);
                        }
                    }
                    arrivedFromIntent = false;
                }
                //Set the data related to the Selection.
                Mentor currentSelectedMentor = (Mentor) mentorSpinner.getSelectedItem();
                //mentorSpinner.setSelection(intentMentorPosition);
                mentorName.setText(currentSelectedMentor.getName());
                mentorPhone.setText(currentSelectedMentor.getPhoneNumber());
                mentorEmail.setText(currentSelectedMentor.getEmail());
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
                intentTermId,
                courseNoteEditText.getText().toString()
        );

        //The primary key is auto-incremented in the database
        editedCourse.setCourseID(intentCourseId); // This will set it to Id that was passed
        repo.updateCourse(editedCourse);

        Intent intent;
        //If there is a valid selected term take us back
        if (StateManager.SelectedTerm.isTermSelected()) {
            intent = new Intent(
                    CourseViewDetailed.this,
                    TermViewDetailed.class);
        } else {
            //Must have come from the course Activity Then
            intent = new Intent(
                    CourseViewDetailed.this,
                    CourseView.class);
        }
        //Change back to the intended destination. And let the user know a state change has occurred.
        Toast.makeText(getApplicationContext(),"Course Data Saved",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void deleteState(View view) {
        Mentor selectedMentor = (Mentor) mentorSpinner.getSelectedItem();
        //Delete the associated course
        Course editedCourse = new Course(
                courseTitle.getText().toString(),
                startText.getText().toString(),
                endText.getText().toString(),
                courseStatus.getSelectedItem().toString(),
                selectedMentor.getMentorID(),
                intentTermId,
                courseNoteEditText.getText().toString()
        );
        //This is seems like a lot of work when I could just delete by the id
        editedCourse.setCourseID(intentCourseId);
        repo.deleteCourse(editedCourse);

        //Declare the intent
        Intent intent;
        //The logic for backing to the next screen.
        //If we arrived here from the TermViewDetailed Screen essentially. We want to return to that
        // Activity. And we will pass in the current intent since we currently the term information.
        if (StateManager.isArrivedToCourseFromTermView()) {
            intent = new Intent(
                    CourseViewDetailed.this,
                    TermViewDetailed.class);
            //Give some extra data to the intent
            Term term = repo.lookupTermById(intentTermId);
            intent.putExtra("id", term.getTermID());
            intent.putExtra("title", term.getTitle());
            intent.putExtra("startDate", term.getStartDate());
            intent.putExtra("endDate" , term.getEndDate());
        } else {
            //Must have come from the course Activity Then and we do not need to pass data back
            //via the intent.
            intent = new Intent(
                    CourseViewDetailed.this,
                    CourseView.class);
        }
        Toast.makeText(getApplicationContext(),"Course Deleted",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void sendNote(View view) {
        //Create the Intent
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String title = courseTitle.getText().toString();
        String message = courseNoteEditText.getText().toString();
        if (!courseNoteEditText.getText().toString().equals("")) {
            //Assign the values of the Intent
            sendIntent.putExtra(Intent.EXTRA_TITLE, title + " Note.");
            sendIntent.putExtra(Intent.EXTRA_TEXT, title + " Notes: " + message);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, title + " Note.");
            startActivity(shareIntent);
        } else {
            Toast.makeText(this,"Please Enter A Note", Toast.LENGTH_SHORT).show();
        }
    }
}



