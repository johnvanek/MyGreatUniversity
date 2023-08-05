package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.Entity.User;
import com.example.android.mygreatuniversity.R;

public class LoginScreen extends AppCompatActivity {
    //Fields
    EditText userNameEditText, passwordEditText;
    TextView errorText;
    ConstraintLayout loginBox, card;
    Button loginButton;

    Boolean isErrorTextVisible = false;

    User user;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        // This disables the night mode which changes the color the text to be unreadable.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //SoftKeyboard stuff
        hideKeyboard(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        //Assigning the Fields
        //Course Assignments
        userNameEditText = findViewById(R.id.editTextTextUsername);
        passwordEditText = findViewById(R.id.editTextTextUsername3);
        errorText = findViewById(R.id.errorText);
        loginBox = findViewById(R.id.LoginBox);
        card = findViewById(R.id.Card);
        loginButton = findViewById(R.id.login);

        //Create the DummyData for the application
        InsertDummyData();

        //Handle the SoftKeyboard behavior
        loginBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(LoginScreen.this);
                userNameEditText.clearFocus();
                passwordEditText.clearFocus();
                return false;
            }
        });

        card.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(LoginScreen.this);
                userNameEditText.clearFocus();
                passwordEditText.clearFocus();
                return false;
            }
        });

        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(LoginScreen.this);
                userNameEditText.clearFocus();
                passwordEditText.clearFocus();
                errorText.setVisibility(View.INVISIBLE);
                isErrorTextVisible = false;
                return false;
            }
        });
    }

    //Logical Code outside of On-Create
    public void attemptLogin(View v) {
        if (isUserValid()) {
            //Valid user proceeds
            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
            startActivity(intent);
        } else {
            //Inform user of problem
            showHideErrorText();
        }
    }

    private boolean isUserValid() {
        // Check the database for valid username & password combination
        // Loop over and compare the users in the database currently only one
        Repo repo = new Repo(getApplication());
        String attemptUsername = userNameEditText.getText().toString();
        String attemptPassword = passwordEditText.getText().toString();
        for (User user: repo.getUsers()) {
            if(user.getUsername().equals(attemptUsername) && (user.getPassword().equals(attemptPassword))){
                return true;
            }
        }
        return false;
    }

    private void showHideErrorText() {
        if (!isErrorTextVisible) {
            errorText.setVisibility(View.VISIBLE);
            isErrorTextVisible = true;
        }
    }

    //-----------||||||||||||||DUMMY-DATA-INSERTION|||||||||||||||||||-----------------*************
    //*************Disable this if you do not want dummy data to inset into the Application*********
    private void InsertDummyData() {
        //Script that will insert dummy data into the application.
        Repo repo = new Repo(getApplication());
        // If there are no users The data has not been created so create the data
        if (repo.getUsers().size() <= 0) {

            //******Users*********
            repo.insertUser(new User("Jane Doe", "janeDoe", "test"));

            //******Courses******
            repo.insertCourse(new Course("Mobile Development", "10/01/2022", "10/30/2022", "In Progress", 1, 1, ""));
            repo.insertCourse(new Course("Operating Systems", "06/01/2022", "07/30/2022", "Completed", 2, 1, ""));
            repo.insertCourse(new Course("Java Fundamentals", "08/01/2022", "08/30/2022", "Completed", 3, 1, ""));
            repo.insertCourse(new Course("Javascript Basics", "12/01/2022", "01/30/2022", "Plan To Take", 4, 1, ""));
            repo.insertCourse(new Course("Design Patterns", "07/01/2022", "07/15/2022", "Dropped", 5, 1, ""));

            //******Mentors******
            //Should always be at least one or else Course view detailed will throw an error.
            repo.insertMentor(new Mentor("Sarah Conor", "561-123-1991", "ResistSky@gmail.com"));
            repo.insertMentor(new Mentor("Lisa Lisa", "039-123-1987", "HamonMaster@gmail.com"));
            repo.insertMentor(new Mentor("Professor Xavier", "010-603-1963", "XMen@gmail.com"));
            repo.insertMentor(new Mentor("Dan Abramov", "203-898-2085", "DanMov@gmail.com"));
            repo.insertMentor(new Mentor("Robert Martin", "943-185-3814", "BobM@gmail.com"));
            //******Terms******
            repo.insertTerm(new Term("Spring2021", "6/01/2021", "12/31/2021"));
            repo.insertTerm(new Term("Fall2022", "01//01/2022", "5/31/2022"));
            repo.insertTerm(new Term("Spring2022", "6/01/2022", "12/31/2022"));
            repo.insertTerm(new Term("Fall2023", "01//01/2023", "5/31/2023"));
            //******Assessments******

            repo.insertAssessment(new Assessment("Android App Dev", "Performance", "10/15/2022", "10/30/2022", 1));
            repo.insertAssessment(new Assessment("OSx86 Basics", "Objective", "06/15/2022", "07/30/2022", 2));
            repo.insertAssessment(new Assessment("Java App-1", "Performance", "08/15/2022", "08/30/2022", 3));
            repo.insertAssessment(new Assessment("ECMAScript Cert.", "Objective", "12/15/2022", "01/30/2022", 4));
            repo.insertAssessment(new Assessment("GangOfFour Test", "Objective", "07/15/2022", "08/30/2022", 5));

            Log.d("DUMMYUSER", "The Dummy users are " + repo.getUsers());
            Log.d("DUMMYCOURSE", "The Dummy courses are " + repo.getCourses());
            Log.d("DUMMYMENTOR", "The Dummy mentors are " + repo.getMentors());
            Log.d("DUMMYASSESSMENT", "The Dummy assessments are " + repo.getAssessments());
        }
    }
}