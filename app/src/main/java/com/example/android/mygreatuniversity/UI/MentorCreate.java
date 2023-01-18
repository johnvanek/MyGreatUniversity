package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;

public class MentorCreate extends AppCompatActivity {
    //**************  START DECLARATIONS *********************
    EditText mentorName, mentorPhoneNumber, mentorEmail;
    ScrollView mentorScrollView;
    LinearLayout mentorLayout;
    CardView mentorCard;
    Repo repo = new Repo(getApplication());

    //****************** END DECLARATIONS **************************************
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        hideKeyboard(this);
        //***************** DISPLAY LOGIC *************
        //Sets the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_create);
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
        mentorCard = findViewById(R.id.mentorCard);

        //************ KEYBOARD HIDING ****************
        mentorName.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mentorName.requestFocus();
                mentorName.setCursorVisible(true);
                return false;
            }
        });

        mentorPhoneNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mentorPhoneNumber.requestFocus();
                mentorPhoneNumber.setCursorVisible(true);
                return false;
            }
        });

        mentorEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mentorEmail.requestFocus();
                mentorEmail.setCursorVisible(true);
                return false;
            }
        });

        mentorCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(MentorCreate.this);
                //Clear the focus and set all the cursors to false
                mentorName.clearFocus();
                mentorName.setCursorVisible(false);
                mentorEmail.clearFocus();
                mentorEmail.setCursorVisible(false);
                mentorPhoneNumber.clearFocus();
                mentorPhoneNumber.setCursorVisible(false);
                return false;
            }
        });
        //Set the IME Actions to clear the focus when done is clicked
        mentorName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edit text
                mentorName.clearFocus();
                mentorName.setCursorVisible(false);
                //And hide that keyboard
                hideKeyboard(MentorCreate.this);
            }
            return false;
        });

        mentorPhoneNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edit text
                mentorPhoneNumber.clearFocus();
                mentorPhoneNumber.setCursorVisible(false);
                //And hide that keyboard
                hideKeyboard(MentorCreate.this);
            }
            //Do not consume the event
            return false;
        });

        mentorEmail.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edit text
                mentorEmail.clearFocus();
                mentorEmail.setCursorVisible(false);
                //And hide that keyboard
                hideKeyboard(MentorCreate.this);
            }
            //Do not consume the event
            return false;
        });

        mentorName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(this);
            }
        });

        myToolbar.setOnClickListener(v -> {
            hideKeyboard(this);
            mentorPhoneNumber.clearFocus();
            mentorPhoneNumber.setCursorVisible(false);
            mentorName.clearFocus();
            mentorName.setCursorVisible(false);
            mentorEmail.clearFocus();
            mentorEmail.setCursorVisible(false);
        });
    }

    public void saveState(View view) {
        Mentor editedMentor = new Mentor(
                mentorName.getText().toString(),
                mentorPhoneNumber.getText().toString(),
                mentorEmail.getText().toString());

        repo.insertMentor(editedMentor);
        //After done performing the update return to the the Course View but first show a toast to indicate a state change.
        Toast.makeText(getApplicationContext(),"Mentor Created",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(
                MentorCreate.this,
                MentorView.class);
        startActivity(intent);
    }

    public void deleteState(View view) {
        //After done performing the update return to the the Course View
        Intent intent = new Intent(
                MentorCreate.this,
                MentorView.class);
        Toast.makeText(getApplicationContext(),"Mentor Discarded",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}



