package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.UI.MainActivity.notificationAlertCount;
import static com.example.android.mygreatuniversity.Utils.Utils.assessmentTypePosition;
import static com.example.android.mygreatuniversity.Utils.Utils.courseStatusPosition;
import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AssessmentViewDetailed extends AppCompatActivity {
    //**************  START DECLARATIONS *********************
    final Calendar CalenderStart = Calendar.getInstance();
    final Calendar CalenderEnd = Calendar.getInstance();

    boolean arrivedFromIntent = true;
    boolean settingBoth = false;
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

    String intentTitle, intentStartDate, intentEndDate, intentType;
    //mentor intent strings
    int intentCourseId;
    int intentAssessmentId;

    Repo repo = new Repo(getApplication());
    // Could use repo to perform a lookup

    //Date References & Declarations
    //This is for Converting dates from strings to date objects
    String format = "MM/dd/yyyy";
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
        assessmentTitle = findViewById(R.id.assessmentTitleEditText);
        assessmentStart = findViewById(R.id.assessmentStart);
        assessmentEnd = findViewById(R.id.assessmentEnd);
        typeSpinner = findViewById(R.id.spinnerType);
        assessmentLayout = findViewById(R.id.assessmentLayout);
        //Assessment Declarations

        assessmentCard = findViewById(R.id.assessmentCard);

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
        editedAssessment.setAssessmentID(intentAssessmentId);
        repo.updateAssessment(editedAssessment);

        Intent intent;
        //If there is a valid selected term take us back
        intent = new Intent(
                AssessmentViewDetailed.this,
                AssessmentView.class);

        //Change back to the intended destination. And let the user know a state change has occurred.
        Toast.makeText(getApplicationContext(), "Assessment Data Saved", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void deleteState(View view) {

        Assessment editedAssessment = new Assessment(
                assessmentTitle.getText().toString(),
                typeSpinner.getSelectedItem().toString(),
                assessmentStart.getText().toString(),
                assessmentEnd.getText().toString(),
                intentCourseId
        );
        //This is seems like a lot of work when I could just delete by the id

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

    public void updateStartAssessmentNotification(MenuItem menuItem) {
        String curStartDate = assessmentStart.getText().toString();
        //curStartDate = dateFormat.format(CalenderStart.getTime());
        Log.d("assessmentChannel", "The String Representation of curStartDate is " + curStartDate);
        //Convert to Date Object
        Date date = null;
        try {
            date = dateFormat.parse(curStartDate);
        } catch (ParseException e) {
            //Catch the parse Exception if there is one
            e.printStackTrace();
        }
        //Pass this intent to the Course receiver. This should trigger
        Long triggerInSeconds = date.getTime();
        //This is for the Course end notification so include a message for that. But really this should
        // only be sent on save not on a change as we could change it back before the rest of this information
        //get modified.
        Intent intent = new Intent(AssessmentViewDetailed.this, AssessmentAlertReceiver.class);
        //This is the information that is going to be passed to the course Receiver
        String assessmentMessageTitle = String.valueOf(assessmentTitle.getText());
        String assessmentMessageBody = String.valueOf(assessmentStart.getText());
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date startTime = null;
        try {
            startTime = sdf.parse(assessmentStart.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = currentTime.getTime() - startTime.getTime();
        //get the abs value
        long days = Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        intent.putExtra("title", "Start Date: " + assessmentMessageTitle);
        intent.putExtra("body", "The Start Date for Course: " + assessmentMessageTitle +
                " was " + assessmentMessageBody + " which was " + days + " days ago.");

        //Create the Pending Intent and pass the intent to it. On the Must recent version of API 33
        //You have to change the Flag explicitly to be mutable or Immutable. But still works with older code.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                //This should still work to generate a unique ID
                AssessmentViewDetailed.this,++notificationAlertCount,
                intent,
                PendingIntent.FLAG_IMMUTABLE);

        //Get from the System the user Preference for Alarms
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //This is where you set the alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP,triggerInSeconds,pendingIntent);
        //alarm
        //Toast.makeText(getApplicationContext(),"Course Start Alert Set!" ,Toast.LENGTH_SHORT).show();
        if(settingBoth){
            Toast.makeText(getApplicationContext(),"Assessment Start&End Alert Set!" ,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Assessment Start Alert Set!" ,Toast.LENGTH_SHORT).show();
        }
    }

    public void updateEndAssessmentNotification(MenuItem menuItem) {
        String curEndDate = assessmentEnd.getText().toString();
        Log.d("assessmentChannel", "The String Representation of curEndDate is " + curEndDate);
        //Convert to Date Object
        Date date = null;
        try {
            date = dateFormat.parse(curEndDate);
        } catch (ParseException e) {
            //Catch the parse Exception if there is one
            e.printStackTrace();
        }
        //Pass this intent to the Course receiver. This should trigger
        Long triggerInSeconds = date.getTime();
        //This is for the Course end notification so include a message for that. But really this should
        // only be sent on save not on a change as we could change it back before the rest of this information
        //get modified.
        Intent intent = new Intent(AssessmentViewDetailed.this, AssessmentAlertReceiver.class);
        //Think to string gets called automatically
        String assessmentMessageTitle = String.valueOf(assessmentTitle.getText());
        String assessmentMessageBody = String.valueOf(assessmentEnd.getText());
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date endTime = null;
        try {
            endTime = sdf.parse(assessmentEnd.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = currentTime.getTime() - endTime.getTime();
        //get the abs value
        long days = Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        intent.putExtra("title", "End Date: " + assessmentMessageTitle);
        intent.putExtra("body", "The End Date for Assessment: " + assessmentMessageTitle +
                " was " + assessmentMessageBody + " which was " + days + " days ago.");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                AssessmentViewDetailed.this,++notificationAlertCount,
                intent,
                PendingIntent.FLAG_IMMUTABLE);

        //Get from the System the user Preference for Alarms
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //This is where you set the alarm
        //Also the calling here might be different because this is where pending intent is passed
        //Where to intent is the sub value
        alarmManager.set(AlarmManager.RTC_WAKEUP,triggerInSeconds,pendingIntent);
        //alarm

        if(settingBoth) {
            //Do nada except set it back false
            settingBoth = false;
        } else {
            Toast.makeText(getApplicationContext(),"Assessment End Alert Set!" ,Toast.LENGTH_SHORT).show();
        }
    }

    public void updateCourseNotifications(MenuItem menuItem) {
        settingBoth = true;
        //This should be reworked to allow for both to be called at the same time.
        updateStartAssessmentNotification(menuItem);
        updateEndAssessmentNotification(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.assessment_detailed_menu, menu);
        return true;
    }
}
