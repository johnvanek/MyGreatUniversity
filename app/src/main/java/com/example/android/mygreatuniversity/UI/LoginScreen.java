package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.android.mygreatuniversity.R;

public class LoginScreen extends AppCompatActivity {
    //TODO Implement Login Auth
    // Implement some sort of Login Screen for Authentication and Authorization.
    // Auth -> the User via the login screen
    // On Enter or button press.
    // Implement Method against database to check -> Username -> Password
    // Against Database should check JDBC method might already exist.

    //Fields
    EditText userNameEditText, passwordEditText;
    TextView errorText;
    ConstraintLayout loginBox, card;
    Button loginButton;

    Boolean isErrorTextVisible = false;

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

        //Handle the Awkward SoftKeyboard behavior
        //This hides the SoftKeyboard onTouch of any of the cards either the login box or the
        // welcome card.

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
                return false;
            }
        });
    }

    //Logical Code outside of On-Create
    public void attemptLogin(View v) {
        //Attempt the login
        //Going to have to query the database if this user exists
        // Need to view the table schema with mysql workbench
        // Currently this table how I think Called from main?

        if(isUserValid()){
            //Log the user to the next scene
            // Which is the main screen
            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
            startActivity(intent);
        } else {
            showHideErrorText();
        }
    }

    private boolean isUserValid(){
        //TODO currently there is no User table
        // There is no way to track which users are logged in or not
        // Technically users should have all this but I think I just
        // Create a User's table if those are valid login.

        //TODO create a user entity
        // Set the methods up that I need in the DAO
        // Mess around with Repo to get the methods I need to check for auth
        // Depends how difficult but I could set this up so that Users have Courses etc
        // Would have to look at this for my Class Diagrams etc.

        return true;
    }

    private void showHideErrorText() {
        if (!isErrorTextVisible) {
            errorText.setVisibility(View.VISIBLE);
            isErrorTextVisible = true;
        } else {
            errorText.setVisibility(View.INVISIBLE);
            isErrorTextVisible = false;
        }
    }
}