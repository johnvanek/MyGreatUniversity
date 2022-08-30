package com.example.android.mygreatuniversity.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CourseViewDetailed extends AppCompatActivity {
    //Declarations for the fields
    EditText courseTitle;
    EditText startText;
    //intent data references
    String title;
    int courseId;
    Repo repo;

    //Date References & Declarations
    final Calendar CalenderStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_detailed);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Define an actionbar reference for shorthand
        ActionBar ab = getSupportActionBar();

        //Set up the back is up when children are present.
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);   //show back button
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Link the Declared textViews from xml file.
        courseTitle = findViewById(R.id.editTextTitle);
        courseId = getIntent().getIntExtra("id", -1);
        //get and assign the intent data
        title = getIntent().getStringExtra("title");

        //Assign the textViews the intent data
        courseTitle.setText(title);

        //DatePicker
        //Get the xml id's for the edit text fields
        startText = findViewById(R.id.editTextStartDate);


        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                CalenderStart.set(Calendar.YEAR, year);
                CalenderStart.set(Calendar.MONTH, month);
                CalenderStart.set(Calendar.DAY_OF_MONTH, day);
                String format = "MM/dd/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
                updateStartDateLabel();
            }
        };

        //Set the listener on the start edit Text
        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseViewDetailed.this, startDatePicker, CalenderStart
                        .get(Calendar.YEAR), CalenderStart.get(Calendar.MONTH),
                        CalenderStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateStartDateLabel(){
        String format = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        startText.setText(simpleDateFormat.format(CalenderStart.getTime()));
    }
}


//    public void saveButton (View view) {
//        Course editedCourse;
//        if(courseId == -1) {
//            int newID = repo.getCourses().get(repo.getCourses().size() - 1).getCourseID() + 1;
//            editedCourse = new Course(newID, )
//        }
//    }
