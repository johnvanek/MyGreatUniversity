package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.assessmentTypePosition;
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

public class AssessmentViewDetailed extends AppCompatActivity {
    //TODO need to rename most of these to their respective class name and when renamed, need to also
    // Rename the id's so that the R.find.by.ID method actually works.
    // Not sure what else needs to be refactored.

    //**************  START DECLARATIONS *********************
    final Calendar CalenderStart = Calendar.getInstance();
    final Calendar CalenderEnd = Calendar.getInstance();

    boolean arrivedFromIntent = true;
    //Field Declarations
    EditText assessmentTitle;
    EditText assessmentStart, assessmentEnd;
    //TextView mentorTextView;
    Spinner typeSpinner;
    //MentorSpinnerAdapter mentorSpinnerAdapter;
    CardView assessmentCard;
    ConstraintLayout assessmentLayout;

    //Intent data references might not need as many of these with lookup methods aka: the ids
    // replace the need to pass so much data through to the intents.

    //TODO need to compare this to what is actually passed in from assessment view and then compare
    // As to what you need. Need to replace the spinner ID here with two string values.

    String intentTitle, intentStartDate, intentEndDate, intentType;
    //mentor intent strings
    int intentCourseId;
    int intentAssessmentId;

    Repo repo = new Repo(getApplication());
    // I mean right here I have access to repo which means that I can make a method to lookup all
    // assessment information by the id.

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
        setContentView(R.layout.activity_assessment_view_detailed);
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

        //Assessment Fields assignments
        //TODO need to redefine the ids for the fields on edit Text fields.
        // These are probably not going to work until the red errors for the rest of the file are gone


        assessmentTitle = findViewById(R.id.assessmentTitleEditText);
        assessmentStart = findViewById(R.id.assessmentStart);
        assessmentEnd = findViewById(R.id.assessmentEnd);
        typeSpinner = findViewById(R.id.spinnerType);
        assessmentLayout = findViewById(R.id.assessmentLayout);
        //Assessment Declarations

        assessmentCard = findViewById(R.id.assessmentCard);

        //TODO have to check what the previous values passed in were make sure that they match up
        // correctly

        //Get and assign the intent data to local variables
        //These default to negative 1 so I will know if the intent passed successfully.

        intentAssessmentId = getIntent().getIntExtra("assessmentID", -1);
        intentCourseId = getIntent().getIntExtra("courseID", -1);
        intentTitle = getIntent().getStringExtra("title");
        intentStartDate = getIntent().getStringExtra("startDate");
        intentEndDate = getIntent().getStringExtra("endDate");
        intentType = getIntent().getStringExtra("type");
//       // Might need Course Data but currently do not need any mentor data.

//       intentMentorId = getIntent().getIntExtra("mentorId", -1);
//       mentorIntentName = getIntent().getStringExtra("mentorName");
//       mentorIntentPhone = getIntent().getStringExtra("mentorPhone");
//       mentorIntentEmail = getIntent().getStringExtra("mentorEmail");

        //Assign the Intent Data to the Fields
        assessmentTitle.setText(intentTitle);
        typeSpinner.setSelection(assessmentTypePosition(this, intentType));
        assessmentStart.setText(intentStartDate);
        assessmentEnd.setText(intentEndDate);

        //************************* DATE-PICKER LOGIC START & END ************************

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

        assessmentTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                assessmentTitle.requestFocus();
                assessmentTitle.setCursorVisible(true);
                return false;
            }
        });

        assessmentTitle.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                assessmentTitle.clearFocus();
                assessmentTitle.setCursorVisible(false);
                hideKeyboard(this);
            }
            return false;
        });

        //I do not think that this actually works
        assessmentTitle.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                assessmentTitle.clearFocus();
                assessmentTitle.setCursorVisible(false);
            }
            hideKeyboard(AssessmentViewDetailed.this);
        });

        assessmentStart.setOnClickListener(view -> {
            //Hide the flickering coming from the title
            assessmentTitle.clearFocus();
            assessmentTitle.setCursorVisible(false);

            hideKeyboard(AssessmentViewDetailed.this);
            new DatePickerDialog(AssessmentViewDetailed.this,
                    startDatePicker, CalenderStart.get(Calendar.YEAR),
                    CalenderStart.get(Calendar.MONTH),
                    CalenderStart.get(Calendar.DAY_OF_MONTH))
                    .show();
            assessmentStart.requestFocus();
        });

        assessmentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(AssessmentViewDetailed.this);
                assessmentTitle.clearFocus();
                assessmentTitle.setCursorVisible(false);
                return false;
            }
        });

        assessmentEnd.setOnClickListener(view -> {
            //Hide the flickering coming from the title
            assessmentTitle.clearFocus();
            assessmentTitle.setCursorVisible(false);
            // And get the current focus
            assessmentTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            hideKeyboard(AssessmentViewDetailed.this);
            new DatePickerDialog(AssessmentViewDetailed.this,
                    endDatePicker, CalenderEnd.get(Calendar.YEAR),
                    CalenderEnd.get(Calendar.MONTH),
                    CalenderEnd.get(Calendar.DAY_OF_MONTH))
                    .show();
            assessmentEnd.requestFocus();
        });

        //************************ KEYBOARD HIDING LOGIC SOFT KEYBOARD ********************

        myToolbar.setOnClickListener(v -> {
            hideKeyboard(this);
            assessmentTitle.clearFocus();
        });

        assessmentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(AssessmentViewDetailed.this);
                assessmentTitle.setCursorVisible(false);
                assessmentTitle.clearFocus();
                return false;
            }

        });

        assessmentStart.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                hideKeyboard(this);
            }
        });

        assessmentEnd.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                hideKeyboard(this);
            }
        });

        typeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                hideKeyboard(AssessmentViewDetailed.this);
                assessmentTitle.setCursorVisible(false);
                assessmentTitle.clearFocus();
                return false;
            }
        });

        //SPINNER POPULATION FROM ROOM DATABASE
        typeSpinner = findViewById(R.id.spinnerType);
        //Part of the Keyboard hiding logic
        typeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                hideKeyboard(AssessmentViewDetailed.this);
                assessmentTitle.setCursorVisible(false);
                assessmentTitle.clearFocus();
                return false;
            }
        });
    }

    private void updateStartDateEditTextField() {
        hideKeyboard(this);
        assessmentStart.setText(dateFormat.format(CalenderStart.getTime()));
    }

    private void updateEndDateEditTextField() {
        hideKeyboard(this);
        assessmentEnd.setText(dateFormat.format(CalenderEnd.getTime()));
    }

    public void saveState(View view) {
        Assessment editedAssessment = new Assessment(
                assessmentTitle.getText().toString(),
                typeSpinner.getSelectedItem().toString(),
                assessmentStart.getText().toString(),
                assessmentEnd.getText().toString(),
                intentCourseId
        );

        //The primary key is auto-incremented in the database
        //This must work since we can only get here from an intent.
//        editedAssessment.setAssessmentID(intentAssessmentId); // This will set it to Id that was passed
        repo.updateAssessment(editedAssessment);

        Intent intent;
        //If there is a valid selected term take us back
        //TODO determine if I still need to roll this type of functionality latter
        // and perhaps make a new line class in StateManager
        intent = new Intent(
                AssessmentViewDetailed.this,
                TermViewDetailed.class);

        //Change back to the intended destination. And let the user know a state change has occurred.
        Toast.makeText(getApplicationContext(), "Assessment Data Saved", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void deleteState(View view) {
        //Delete the associated course
        Assessment editedAssessment = new Assessment(
                assessmentTitle.getText().toString(),
                typeSpinner.getSelectedItem().toString(),
                assessmentStart.getText().toString(),
                assessmentEnd.getText().toString(),
                intentCourseId
        );
        //This is seems like a lot of work when I could just delete by the id
        //TODO might need to reimplement this once more than one way to arrive to this class.
        editedAssessment.setAssessmentID(intentAssessmentId);
        repo.deleteAssessment(editedAssessment);

        //Declare the intent
        Intent intent;
        //If we arrived here from the TermViewDetailed Screen essentially. We want to return to that
        // activity. And we will pass in the current intent since we currently the term information.

        intent = new Intent(
                AssessmentViewDetailed.this,
                AssessmentView.class);

        Toast.makeText(getApplicationContext(),"Assessment Deleted",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
