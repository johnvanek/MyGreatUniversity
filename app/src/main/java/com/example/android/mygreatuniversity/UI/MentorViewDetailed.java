package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;

public class MentorViewDetailed extends AppCompatActivity {
    //**************  START DECLARATIONS *********************
    EditText mentorName, mentorPhoneNumber, mentorEmail;
    ScrollView mentorScrollView;
    LinearLayout mentorLayout;
    CardView mentorCard;
    String intentName, intentPhoneNumber, intentEmail;
    int intentMentorId;
    Repo repo = new Repo(getApplication());

    //****************** END DECLARATIONS **************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
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
        mentorCard = findViewById(R.id.mentorCard);
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
        //TODO Copy the layout of the course view detailed examine the xml structure therein lies
        // success

        mentorName.setOnClickListener(view -> {
            mentorName.requestFocus();
            mentorName.setCursorVisible(true);
        });

        mentorCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(MentorViewDetailed.this);
                mentorName.clearFocus();
                mentorName.setCursorVisible(false);
                mentorEmail.clearFocus();
                mentorPhoneNumber.clearFocus();
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
                hideKeyboard(MentorViewDetailed.this);
            }
            return false;
        });

        mentorPhoneNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edit text
                mentorPhoneNumber.clearFocus();
                //And hide that keyboard
                hideKeyboard(MentorViewDetailed.this);
            }
            //Do not consume the event
            return false;
        });
        //For some reason this has a return key

        mentorEmail.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                //Clear focus here from edit text
                mentorEmail.clearFocus();
                //And hide that keyboard
                hideKeyboard(MentorViewDetailed.this);
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
            mentorPhoneNumber.clearFocus();
            mentorName.clearFocus();
            mentorEmail.clearFocus();
        });
    }


    public void saveState(View view) {
        Mentor editedMentor = new Mentor(
                mentorName.getText().toString(),
                mentorPhoneNumber.getText().toString(),
                mentorEmail.getText().toString());

        //Convenience methods work by comparing the primary key
        editedMentor.setMentorID(intentMentorId); // This will set it to Id that was passed
        repo.updateMentor(editedMentor);
        //After done performing the update return to the the Course View
        Intent intent = new Intent(
                MentorViewDetailed.this,
                MentorView.class);
        startActivity(intent);
    }

    public void deleteState(View view) {
        Mentor editedMentor = new Mentor(
                mentorName.getText().toString(),
                mentorPhoneNumber.getText().toString(),
                mentorEmail.getText().toString());

        editedMentor.setMentorID(intentMentorId); // This will set it to Id that was passed
        repo.deleteMentor(editedMentor);
        //After done performing the update return to the the Course View
        Intent intent = new Intent(
                MentorViewDetailed.this,
                MentorView.class);
        startActivity(intent);
    }
}



