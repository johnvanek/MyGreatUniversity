package com.example.android.mygreatuniversity.UI;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.R;

public class CourseViewDetailed extends AppCompatActivity {
    //Declarations for the fields
    EditText courseTitle;

    //intent data references
    String title;
    int courseId;
    Repo repo;
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
        courseTitle = findViewById(R.id.editTextSelectedCourseTitle);
        courseId = getIntent().getIntExtra("id", -1);
        //get and assign the intent data
        title = getIntent().getStringExtra("title");

        //Assign the textViews the intent data
        courseTitle.setText(title);

        //Add the DatePicker for
    }

//    public void saveButton (View view) {
//        Course editedCourse;
//        if(courseId == -1) {
//            int newID = repo.getCourses().get(repo.getCourses().size() - 1).getCourseID() + 1;
//            editedCourse = new Course(newID, )
//        }
//    }
}