package com.example.android.mygreatuniversity.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Token test take 2 with renewed credentials
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        // This disables the night mode which changes the color the text to be unreadable.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}