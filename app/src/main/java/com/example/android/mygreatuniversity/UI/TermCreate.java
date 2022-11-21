package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
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

public class TermCreate extends AppCompatActivity {
    //TODO
    // Implement the features to add dynamically, create on click. And persist data.
    // Make sure that the user cannot create empty terms send up a toast or something.

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
    //TODO implement the add feature and pasta the dynamic part.
    //XML Fields
    List<Course> termCourses;
    ConstraintLayout termLayout;
    Spinner courseSpinner;
    CourseSpinnerAdapter courseSpinnerAdapter;
    View anyView;
    TermCourseAdapter termCourseAdapter;
    //Date References & Declarations
    RecyclerView termCourseRecyclerView;

    String format = "MM/dd/yy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
    DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Call the Super
        super.onCreate(savedInstanceState);
        //Stop the title from automatically coming into focus
        //Token test
        hideKeyboard(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        //***************** DISPLAY LOGIC *************
        setContentView(R.layout.activity_term_create);
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
        termCourses = repo.getTermCourses(intentTermID);
        termCourseLayout = findViewById(R.id.termLayout);
        termCourseRecyclerView = findViewById(R.id.selectedTermRecyler);

        //************ INTENT DATA PASSING ****************
        intentTitle = getIntent().getStringExtra("title");
        intentStart = getIntent().getStringExtra("startDate");
        intentEnd = getIntent().getStringExtra("endDate");
        //This will crash if not valid id
        intentTermID = getIntent().getIntExtra("id", -1);


        //************ Recycler View / Spinner Logic  ****************
        // Set the TermAdapter and LayoutManger
        final TermCourseAdapter termCourseAdapter = new TermCourseAdapter(this);
        TermCreate.this.termCourseRecyclerView.setAdapter(termCourseAdapter);
        TermCreate.this.termCourseRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Populate the data for the list
        //TODO - ERROR when there is a new term being created how do I know the ID?
        // Since the ID's are auto-generated by the database. Any Course that is Removed that is not
        // Assigned get defaulted to zero which is what is being read in on creation.
        // In Order to fix this when created query the database for the next up term ID.

        termCourseAdapter.setTermCourses(termCourses);
        termCourseRecyclerView.setAdapter(termCourseAdapter);

        //**************SPINNER LOGIC******************************
        courseSpinner = findViewById(R.id.termCreateSpinner);
        //Part of the Keyboard hiding logic
        courseSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                hideKeyboard(TermCreate.this);
                termTitle.setCursorVisible(false);
                termTitle.clearFocus();
                return false;
            }
        });

        //This is a list of courses from the room database
        List<Course> courseList = repo.getCourses();
        //This converts the list from Courses to an array to be used by the mentor spinner adapter.
        Course[] courseArray = courseList.toArray(new Course[0]);
        courseSpinnerAdapter = new CourseSpinnerAdapter(TermCreate.this,
                R.layout.mentor_spinner_item,
                courseArray);
        courseSpinnerAdapter.setDropDownViewResource(R.layout.mentor_spinner_item);
        //mentorSpinner.setSelection(intentMentorId - 1);
        //set the adapter
        courseSpinner.setAdapter(courseSpinnerAdapter);

        //SPINNER OVERRIDES
        //Override the selected behavior
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

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
            hideKeyboard(TermCreate.this);
        });

        //Set onClick Listeners for both the Start and End XML EditTexts -> that creates the new
        //DatePicker Dialog's mentioned above and show them.
        termStart.setOnClickListener(view -> {
            //Hide the flickering coming from the title
            termTitle.setCursorVisible(false);
            termTitle.clearFocus();
            hideKeyboard(TermCreate.this);
            new DatePickerDialog(TermCreate.this,
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
                hideKeyboard(TermCreate.this);
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
                hideKeyboard(TermCreate.this);
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
            hideKeyboard(TermCreate.this);
            new DatePickerDialog(TermCreate.this,
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
    }
public void addCourse(View view) {
    //If the course does not already exist add it to the term
    Course curCourse = (Course) courseSpinner.getSelectedItem();
    if(isCourseUnique(curCourse)) {
        //Change the term Id for this course
        Snackbar snackbar = Snackbar.make(anyView,"If this Course is already Assigned, " +
                "Add-Action becomes a Replace! \n\nDo you wish to continue?",Snackbar.LENGTH_LONG);
        snackbar.setDuration(8000);
        snackbar.setTextMaxLines(30);

        snackbar.setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reassign the term ID for the Course
                curCourse.setTermID(intentTermID);
                repo.updateCourse(curCourse);
                //Show a toast alerting the user to a state change.
                Toast.makeText(getApplicationContext(),"Term Course Added.",Toast.LENGTH_SHORT).show();
                // And regenerate the list dynamically
                //TODO where applicable replace the practice of sending the user back.
                // On data change.
                termCourses = repo.getTermCourses(intentTermID);
                termCourseAdapter.setTermCourses(termCourses);
                termCourseRecyclerView.setAdapter(termCourseAdapter);
            }
        });
        snackbar.show();
    } else {
        Toast.makeText(getApplicationContext(),"Course Already in Term.",Toast.LENGTH_SHORT).show();
    }
}
    private boolean isCourseUnique (Course course) {
        boolean flag = true;
        for (Course termCourse: termCourses) {
            if(course.getCourseID() == termCourse.getCourseID()) {
                flag = false;
            }
        }
        return flag;
    }

    private void updateStartDateEditTextField() {
        hideKeyboard(this);
        termStart.setText(dateFormat.format(CalenderStart.getTime()));
    }

    private void updateEndDateEditTextField() {
        hideKeyboard(this);
        termEnd.setText(dateFormat.format(CalenderEnd.getTime()));
    }

    public void createTerm(View view) {
        if(TextUtils.isEmpty(termTitle.getText().toString()) || TextUtils.isEmpty(termStart.getText().toString())
        || TextUtils.isEmpty(termEnd.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Fill Out Fields", Toast.LENGTH_SHORT).show();
        } else {
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
            // TODO This is performing an update I need it to perform a create
            repo.insertTerm(editedTerm);
            //And then route the user back to the term view and send up a toast to indicate that the state

            Intent intent = new Intent(
                    TermCreate.this,
                    TermView.class);
            //And let the user know a state change has occurred.
            Toast.makeText(getApplicationContext(), "Term Created", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
}
