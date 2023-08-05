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
import android.view.Menu;
import android.view.MenuInflater;
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

public class AssessmentCreate extends AppCompatActivity {
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

    Repo repo = new Repo(getApplication());
    // Could use repo to perform a lookup

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
        setContentView(R.layout.activity_assessment_create);
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
        assessmentTitle = findViewById(R.id.assessmentTitleEditText);
        assessmentStart = findViewById(R.id.assessmentStart);
        assessmentEnd = findViewById(R.id.assessmentEnd);
        typeSpinner = findViewById(R.id.spinnerType);
        assessmentLayout = findViewById(R.id.assessmentLayout);
        //Assessment Declarations

        assessmentCard = findViewById(R.id.assessmentCard);

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
            hideKeyboard(AssessmentCreate.this);
        });

        assessmentStart.setOnClickListener(view -> {
            //Hide the flickering coming from the title
            assessmentTitle.clearFocus();
            assessmentTitle.setCursorVisible(false);

            hideKeyboard(AssessmentCreate.this);
            new DatePickerDialog(AssessmentCreate.this,
                    startDatePicker, CalenderStart.get(Calendar.YEAR),
                    CalenderStart.get(Calendar.MONTH),
                    CalenderStart.get(Calendar.DAY_OF_MONTH))
                    .show();
            assessmentStart.requestFocus();
        });

        assessmentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(AssessmentCreate.this);
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
            hideKeyboard(AssessmentCreate.this);
            new DatePickerDialog(AssessmentCreate.this,
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
                hideKeyboard(AssessmentCreate.this);
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
                hideKeyboard(AssessmentCreate.this);
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
                hideKeyboard(AssessmentCreate.this);
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
        //Set this to negative 1 since it has not been assigned a course yet
        Assessment editedAssessment = new Assessment(
                StateManager.loggedInUserID,
                assessmentTitle.getText().toString(),
                typeSpinner.getSelectedItem().toString(),
                assessmentStart.getText().toString(),
                assessmentEnd.getText().toString(),
                -1
        );

        repo.insertAssessment(editedAssessment);
        Intent intent;
        //If there is a valid selected term take us back
        intent = new Intent(
                AssessmentCreate.this,
                AssessmentView.class);

        //Change back to the intended destination. And let the user know a state change has occurred.
        Toast.makeText(getApplicationContext(), "Assessment Created", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void deleteState(View view) {
        //Declare the intent
        Intent intent;

        intent = new Intent(
                AssessmentCreate.this,
                AssessmentView.class);

        Toast.makeText(getApplicationContext(),"Assessment Discarded",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
