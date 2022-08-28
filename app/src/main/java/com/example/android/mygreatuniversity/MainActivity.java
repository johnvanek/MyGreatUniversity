package com.example.android.mygreatuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sets the toolbar defined in the layout as the action bar
        //Which enables me to use more methods.
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //This
        //getSupportActionBar().setHomeAsUpIndicator();//This changes the logo for the up indicator
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
    }


    //Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    public void gotoTermView(MenuItem item) {
        System.out.println("hello");
        Intent intent = new Intent(MainActivity.this, TermView.class);
        startActivity(intent);
    }
}