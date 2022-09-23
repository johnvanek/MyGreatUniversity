package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.R;

public class MentorViewDetailed extends AppCompatActivity {
    //**************  START DECLARATIONS *********************
    EditText mentorName, mentorPhoneNumber, mentorEmail;
    ScrollView mentorScrollView;
    LinearLayout mentorLayout;
    String intentName, intentPhoneNumber, intentEmail;
    int intentMentorId;


    //****************** END DECLARATIONS **************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideKeyboard(this);
        //***************** DISPLAY LOGIC *************
        //Sets the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_view_detailed);
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
        mentorName = findViewById(R.id.editTextName);
        mentorPhoneNumber = findViewById(R.id.editTextPhone);
        mentorEmail = findViewById(R.id.editTextEmail);
        mentorScrollView = findViewById(R.id.MentorDetailedScrollView);
        mentorLayout = findViewById(R.id.MentorLinear);
        //Get and assign the intent data to local variables
        intentMentorId = getIntent().getIntExtra("id", -1);
        intentName = getIntent().getStringExtra("name");
        intentPhoneNumber = getIntent().getStringExtra("phone");
        intentEmail = getIntent().getStringExtra("email");

        //Assign the XML Fields the values from the intents or that have been edited
        mentorName.setText(intentName);
        mentorPhoneNumber.setText(intentPhoneNumber);
        mentorEmail.setText(intentEmail);

        //************ KEYBOARD HIDING ****************

        mentorLayout.setOnClickListener(v -> {
            //clear the focus and clear the keyboard
            hideKeyboard(this);
            mentorPhoneNumber.clearFocus();
            mentorName.clearFocus();
            mentorEmail.clearFocus();
        });

        mentorName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edit text
                mentorName.clearFocus();
                //And hide that keyboard
                hideKeyboard(MentorViewDetailed.this);
            }
            return false;
        });

        mentorName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(this);
            }
        });

        myToolbar.setOnClickListener(v -> {
            mentorPhoneNumber.clearFocus();
            mentorName.clearFocus();
            mentorEmail.clearFocus();
            hideKeyboard(this);
        });

        // TODO save the state
//    public void saveState(View view) {
//        Course editedCourse = new Course(
//                courseTitle.getText().toString(),
//                startText.getText().toString(),
//                endText.getText().toString(),
//                courseStatus.getSelectedItem().toString());

        //Convenience methods work by comparing the primary key
//        editedCourse.setCourseID(intentCourseId); // This will set it to Id that was passed
//        repo.updateCourse(editedCourse);
//        //After done performing the update return to the the Course View
//        Intent intent = new Intent(
//                CourseViewDetailed.this,
//                CourseView.class);
//        startActivity(intent);
//    }

        // TODO add an option to delete the state
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
}



