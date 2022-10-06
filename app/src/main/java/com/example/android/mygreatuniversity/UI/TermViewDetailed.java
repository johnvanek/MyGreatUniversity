package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermViewDetailed extends AppCompatActivity {
    //**************  START DECLARATIONS *********************
    final Calendar CalenderStart = Calendar.getInstance();
    final Calendar CalenderEnd = Calendar.getInstance();
    //**************  FIELDS *********************
    //Intent Data
    String intentTitle, intentStart, intentEnd;
    int intentTermID;
    //XML Fields
    EditText termTitle, termStart, termEnd;
    //Repo Access & data
    Repo repo = new Repo(getApplication());
    List<Course> termCourses;
    ConstraintLayout termLayout;
    //Date References & Declarations
    String format = "MM/dd/yy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
    DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Call the Super
        super.onCreate(savedInstanceState);
        //Stop the title from automatically coming into focus
        hideKeyboard(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        //***************** DISPLAY LOGIC *************
        setContentView(R.layout.activity_term_view_detailed);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        //Set up the back is up when children are present.
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //************ XML Field Assignment ****************
        termTitle = findViewById(R.id.termTitle);
        termStart = findViewById(R.id.termStart);
        termEnd = findViewById(R.id.termEnd);
        termLayout = findViewById(R.id.LayoutTerm);

        //************ Data PrePopulation ****************
        if (StateManager.SelectedTerm.getHasSavedData()) {
            //************ Data Recovery From StateManager ****************
            //Set the termTitle
            termTitle.setText(StateManager.SelectedTerm.getTermTitle());
            termStart.setText(StateManager.SelectedTerm.getTermStart());
            termEnd.setText(StateManager.SelectedTerm.getTermEnd());
            //Get the term Id from the saved state and then call from the repo
            termCourses = repo.getTermCourses(StateManager.SelectedTerm.getTermID());
            //Set state back to false
            StateManager.SelectedTerm.setHasSavedData(false);
        } else {
            //************ INTENT DATA PASSING ****************
            //Assign the Intent Data
            intentTitle = getIntent().getStringExtra("title");
            intentStart = getIntent().getStringExtra("startDate");
            intentEnd = getIntent().getStringExtra("endDate");
            //This will crash if not valid id
            intentTermID = getIntent().getIntExtra("id", -1);
            // Set fields to the Intent Data
            termTitle.setText(intentTitle);
            termStart.setText(intentStart);
            termEnd.setText(intentEnd);

            // Get the term Id from the Intent and pass that to the repo
            termCourses = repo.getTermCourses(intentTermID);
        }
        //Populate the Term List for the Recycler view
        RecyclerView recyclerView = findViewById(R.id.selectedTermRecyler);
        // Set the TermAdapter and LayoutManger
        final TermCourseAdapter termCourseAdapter = new TermCourseAdapter(this);
        recyclerView.setAdapter(termCourseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Get the list of mentors from the repository
        List<Mentor> mentors = repo.getMentors();
        //Set the data for the adapters
        termCourseAdapter.setTermCourses(termCourses);
        termCourseAdapter.setMentors(mentors);

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

        termTitle.setOnClickListener(view -> {
            termTitle.requestFocus();
            termTitle.setCursorVisible(true);
        });

        termTitle.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edit text
                //by clearing the focus here Im messing with the view
                //courseTitle.clearFocus();
                //And hide that keyboard
                hideKeyboard(this);
            }
            return false;
        });

        termTitle.setOnFocusChangeListener((v, hasFocus) -> {
            hideKeyboard(TermViewDetailed.this);
        });

        //Set onClick Listeners for both the Start and End XML EditTexts -> that creates the new
        //DatePicker Dialog's mentioned above and show them.
        termStart.setOnClickListener(view -> {
            //Hide the flickering coming from the title

            termTitle.setCursorVisible(false);

            hideKeyboard(TermViewDetailed.this);
            new DatePickerDialog(TermViewDetailed.this,
                    startDatePicker, CalenderStart.get(Calendar.YEAR),
                    CalenderStart.get(Calendar.MONTH),
                    CalenderStart.get(Calendar.DAY_OF_MONTH))
                    .show();
            //courseTitle.clearFocus();
            termStart.requestFocus();
        });

        //************************ KEYBOARD HIDING LOGIC SOFT KEYBOARD ********************

        myToolbar.setOnClickListener(v -> {
            hideKeyboard(this);
            termTitle.clearFocus();
        });

        termLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(TermViewDetailed.this);
                termTitle.clearFocus();
                return false;
            }
        });

        termEnd.setOnClickListener(view -> {
            //Hide the flickering coming from the title
            termTitle.setCursorVisible(false);
            // And get the current focus
            termTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            hideKeyboard(TermViewDetailed.this);
            new DatePickerDialog(TermViewDetailed.this,
                    endDatePicker, CalenderEnd.get(Calendar.YEAR),
                    CalenderEnd.get(Calendar.MONTH),
                    CalenderEnd.get(Calendar.DAY_OF_MONTH))
                    .show();
            termEnd.requestFocus();
        });

        termStart.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                hideKeyboard(this);
            }
        });

        termEnd.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                hideKeyboard(this);
            }
        });

    }

    @Override
    public void onDestroy() {
        //TODO add the rest of the fields to be stored
        // Start Date
        // End Date
        // Modify the onSave and Delete functionality for this.

        //For some reason on going back android destroys then recreates this activity on back
        //Save the state into stateManager if the user goes back from the selected course
        StateManager.SelectedTerm.setTermID(intentTermID);
        StateManager.SelectedTerm.setTermTitle(termTitle.getText().toString());
        StateManager.SelectedTerm.setTermStart(termStart.getText().toString());
        StateManager.SelectedTerm.setTermEnd(termEnd.getText().toString());
        //We now have saved some data change to true
        StateManager.SelectedTerm.setHasSavedData(true);
        super.onDestroy();
    }

    private void updateStartDateEditTextField() {
        hideKeyboard(this);
        termStart.setText(dateFormat.format(CalenderStart.getTime()));
    }

    private void updateEndDateEditTextField() {
        hideKeyboard(this);
        termEnd.setText(dateFormat.format(CalenderEnd.getTime()));
    }

    public void deleteAllCourses() {
        //TODO implement functionality here to delete all the associated courses
        // Create a toast evaluators like to see toast or messages for state changes
    }
}
