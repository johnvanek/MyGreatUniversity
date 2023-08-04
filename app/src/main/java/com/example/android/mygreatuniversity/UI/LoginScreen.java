package com.example.android.mygreatuniversity.UI;

import static com.example.android.mygreatuniversity.Utils.Utils.hideKeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.android.mygreatuniversity.R;

public class LoginScreen extends AppCompatActivity {
    //TODO
    // Create new entity faculty -> base class -> other staff extends from this class
    // Rework other Entity Classes so that they inherit base properties from Faculty
    // Add a new Class in Entity to Round out the Faculty.
    // This meets the requirement for polymorphism

    //TODO Implement Login Auth
    // Implement some sort of Login Screen for Authentication and Authorization.
    // Auth -> the User via the login screen
    // On Enter or button press.
    // Implement Method against database to check -> Username -> Password
    // Against Database should check JDBC method might already exist.

    //TODO Add Search Functionality to the Program
    // Add additional business classes for Faculty etc.

    //TODO make the softkeyboard behave the same way it does in the rest of the application

    //TODO make the Login Onclick event that has two functions
    // One function to check for errors when logging in
    // And another to display a message in the box or make the borders red if there is no correct login

    //Fields
    EditText userNameEditText, passwordEditText;
    ConstraintLayout loginBox, card;
    Button loginButton;

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

        //Logical Code

    }
}