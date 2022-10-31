package com.example.android.mygreatuniversity.UI;

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
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
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

public class TermViewDetailed extends AppCompatActivity {
    //TODO need to add an overflow or something to add courses to the term probably pull the values
    // From a spinner or something.
    // Could add an Icon that OnClick is going to do open a dialog that would allow the users to select
    // from a spinner and add a Course to a selected term.
    // Or I could add a spinner here and add the current selection when the user clicks the add
    // Button that seems like most simple solution
    // The logic will be elaborated on further within the page.
    // Most of the work for this is going to be coming from the layout file itself.

    //**************  START DECLARATIONS *********************
    final Calendar CalenderStart = Calendar.getInstance();
    final Calendar CalenderEnd = Calendar.getInstance();
    ConstraintLayout termCourseLayout;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Context context = getApplicationContext();
//        CharSequence text = "Hello From onCreate!";
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
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
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //************ XML Field Assignment ****************
        termTitle = findViewById(R.id.termTitle);
        termStart = findViewById(R.id.termStart);
        termEnd = findViewById(R.id.termEnd);
        termLayout = findViewById(R.id.LayoutTerm);
        termCourseLayout = findViewById(R.id.termLayout);
        //************ Data PrePopulation ****************
        //Can now just always set this data from the repo
        //Set state back to false

        intentTitle = getIntent().getStringExtra("title");
        intentStart = getIntent().getStringExtra("startDate");
        intentEnd = getIntent().getStringExtra("endDate");
        //This will crash if not valid id
        intentTermID = getIntent().getIntExtra("id", -1);

        //************ INTENT DATA PASSING ****************
        //If we have valid intent data use it.
        if (intentTermID != 0 && intentTermID != -1) {
            // Set fields to the Intent Data
            termTitle.setText(intentTitle);
            termStart.setText(intentStart);
            termEnd.setText(intentEnd);
            termCourses = repo.getTermCourses(intentTermID);
            // When this is created and valid we should store the reference of this variable
            // Into StateManager as is the currently selected term if we have made it to this screen
            StateManager.SelectedTerm.setTermID(intentTermID); //This is a Valid term ID
            //This will override the current value everyTime there is a valid ID.
            StateManager.SelectedTerm.setIsTermSelected(true);
        } else {
            //The intent is no longer valid and as such we have to use the backup that is
            // Saved in the StateManager.
            int stateID = StateManager.SelectedTerm.getTermID();
            Term stateTerm = repo.lookupTermById(stateID);
            termTitle.setText(stateTerm.getTitle());
            termStart.setText(stateTerm.getStartDate());
            termEnd.setText(stateTerm.getEndDate());
            termCourses = repo.getTermCourses(stateID);
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

        termTitle.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edit text
                termTitle.clearFocus();
                termTitle.setCursorVisible(false);
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
            termTitle.clearFocus();
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
        termTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(TermViewDetailed.this);
                termTitle.requestFocus();
                termTitle.setCursorVisible(true);
                return false;
            }
        });

        myToolbar.setOnClickListener(v -> {
            hideKeyboard(this);
            termTitle.clearFocus();
            termTitle.setCursorVisible(false);
        });

        termLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(TermViewDetailed.this);
                termTitle.clearFocus();
                termTitle.setCursorVisible(false);
                return false;
            }
        });

        termEnd.setOnClickListener(view -> {
            // Hide the flickering coming from the title
            termTitle.setCursorVisible(false);
            termTitle.clearFocus();
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
                termTitle.clearFocus();
                termTitle.setCursorVisible(false);
                hideKeyboard(this);
            }
        });

        termEnd.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                termTitle.clearFocus();
                termTitle.setCursorVisible(false);
                hideKeyboard(this);
            }
        });
        //TODO Add to the menu-bar of this activity a new overflow item to add terms which would take them to
        // A new screen activity etc. This functionality should be similar to The add screen overflow that is to be added.
        // This is probably the best way to do this create a menu item that go to a new create to create.
        // I would need menu-items and creation screens for
        // Course
        // Term
        // Assessment
        // Mentor
    }

    private void updateStartDateEditTextField() {
        hideKeyboard(this);
        termStart.setText(dateFormat.format(CalenderStart.getTime()));
    }

    private void updateEndDateEditTextField() {
        hideKeyboard(this);
        termEnd.setText(dateFormat.format(CalenderEnd.getTime()));
    }

    public void saveState(View view) {
        Term editedTerm = new Term(
                termTitle.getText().toString(),
                termStart.getText().toString(),
                termEnd.getText().toString()
        );
        //This is needed due to the fact that this can sometimes be defaulted to the wrong primary
        // key.
        if (intentTermID != -1 && intentTermID != 0) {
            editedTerm.setTermID(intentTermID);
        } else {
            editedTerm.setTermID(StateManager.SelectedTerm.getTermID());
        }
        // And finally once that is done call the update method from repo.
        repo.updateTerm(editedTerm);
        //And then route the user back to the term view and send up a toast to indicate that the state

        Intent intent;
        //If create the intent and take us back
        intent = new Intent(
                TermViewDetailed.this,
                TermView.class);
        //And let the user know a state change has occurred.
        Toast.makeText(getApplicationContext(), "Term Data Saved", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void deleteState(View view) {
        //If clicked this should only delete the term if there are no associated courses
        if (doesTermHaveEnrolledCourses()) {
            showSnackbarMessageDeletionAction();
        } else {
            // Just let the user know that a state change has occurred
            Toast.makeText(getApplicationContext(), "Term Deleted", Toast.LENGTH_SHORT).show();
            // route them back to the the term view.
            Intent intent = new Intent(
                    TermViewDetailed.this,
                    TermView.class);
            //Create term to delete kinda cumbersome
            Term termToDelete;
            if (intentTermID != -1 && intentTermID != 0) {
                termToDelete = repo.lookupTermById(intentTermID);
            } else {
                termToDelete = repo.lookupTermById(StateManager.SelectedTerm.getTermID());
            }
            repo.deleteTerm(termToDelete);
            //Start the intent
            startActivity(intent);
        }
    }

    private boolean doesTermHaveEnrolledCourses() {
        //If there are now elements in the list it must not have any courses
        if (intentTermID != -1 && intentTermID != 0) {
            return repo.getTermCourses(intentTermID).size() >= 1;
        } else {
            return repo.getTermCourses((StateManager.SelectedTerm.getTermID())).size() >= 1;
        }
    }

    private void showSnackbarMessageDeletionAction() {
        Snackbar snackbar = Snackbar.make(termCourseLayout, "You cannot delete a term while still enrolled in courses. If you would like to remove all Courses, Please confirm by clicking the action.", Snackbar.LENGTH_LONG);
        snackbar.setDuration(6000);
        snackbar.setTextMaxLines(30);

        snackbar.setAction("Remove All Courses", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Need to call the update course method for every course than is in this list
                termCourses.forEach(course -> {
                    //This set the term Id for each course to 0 effectively making them -
                    // Unassociated with any Terms.
                    course.setTermID(0);
                    repo.updateCourse(course);
                });

                //Start an intent and go back one level

                Intent intent = new Intent(TermViewDetailed.this, TermView.class);
                //Create a toast here if possible
                Toast.makeText(getApplicationContext(), "All Courses Removed from Term", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        snackbar.show();
    }
}
