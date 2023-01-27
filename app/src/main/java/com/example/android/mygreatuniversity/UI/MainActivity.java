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

public class MainActivity extends AppCompatActivity {
    //should actually pre-increment this before I call the count
    public static int notificationAlertCount;
    //TODO Remaining UI
    // Last UI Changes to make it I have time
    // Change the coloring for the red and purple listing of things [Completed][Light Blue White Rectangles]
    // Redo the Main-Menu to become like a news section or something. [In-Progress]

    //TODO Rest
    // ****------Current Task [Create the BroadCast Receivers]----****
    // 1) [Completed]Need to add the Crud operations via an overflow menu for all items
    // 2) [Completed] - Broadcast Receivers Course End/Start Assessment End Start
    // 3) [In-Progress]Create the landscape view of everything
    // ----- Should be an option in the Android Studio settings to generate a landscape view.
    // 4) [TO-DO]-Then have to create the storyboard for the application.
    // 5) [TO-DO]-Then have to answer the section about the paper writing requirements for.
    // 6) [TO-DO]-Sign the Apk and take a picture Submit to the app store - optional to submit

    //TODO-Last-CheckList-Per The Task View - [Completed]
    // A: Mobile-Version The Android mobile application is a version 8.0 or higher, loads properly, and all Android project files, APK, and signed apk files are in one zipped.zip file.[TO-DO]
    // A1:TERM FUNCTIONAL REQUIREMENTS: The mobile application allows the user to enter all term titles and all start and end dates for each term. [Completed]
    // A2:TERM ADDITION FEATURE: The features included in the mobile application are coded to allow the user to add an unlimited number of terms. [Completed]
    // A3:VALIDATION IMPLEMENTATION: The mobile application includes validation to prevent the user from deleting a term when courses have been assigned to that term. [Completed]
    // A4A:COURSE ADDITION: The mobile application is coded so that the user can add an unlimited number of courses to each term. [Completed]
    // A4B:LIST OF COURSES: The mobile application allows the user to display a list of courses associated with each term. [Completed]
    // A4C:TERM DETAILS: The mobile application allows the user to display a detailed view of each term and the view includes the term title, the start date, and the end date for each term [Completed]
    // A5:COURSE DETAILS: The mobile application allows the user to enter all the given details for each course. [Completed]
    // A6A:ASSESSMENT ADDITION: The mobile application is coded so that the user can add as many as 5 assessments to each course. [Completed] There is no life but they will replace.
    // A6B:OPTIONAL NOTES ADDITION: The mobile application allows the user to add optional notes within each course. [Completed]
    // A6C:COURSE INFORMATION: The mobile application allows the user to enter, edit, and delete any information for each course. [Completed]
    // A6D:OPTIONAL NOTES DISPLAY: The mobile application allows the user to display optional notes within each course. [Completed]
    // A6E:DETAILED VIEW: The mobile application allows the user to display a detailed view of the course information for each course, including the end date for each. [Completed]
    // A6F:ALERTS FOR COURSES: The mobile application allows the user to set alerts for both the start and end date of each course and the alerts can trigger when the application is not running. [Completed]
    // A6G:SHARING FEATURES: The mobile application allows the user to share messages or email that automatically populates with the notes. [Completed] messages
    // A7A:ASSESSMENTS FOR EACH COURSE: The mobile application allows the user to add performance and objective assessments for each course, including the title of the assessment and the end date for each assessment. [Completed]
    // A7B:ASSESSMENT INFORMATION: The mobile application allows the user to enter, edit, and delete all assessment information. [Completed]
    // A7C:ALERTS FOR GOAL DATES: The mobile application allows the user to set alerts for all start and end dates for each assessment.[Completed]

    //TODO - [In-Progress]
    // B:SCREEN LAYOUTS The screen designs include the layout for each given screen, as described, and includes appropriate GUI elements for each layout. [In-Progress] - Need Landscape
    // D:STORYBOARD: The storyboard demonstrates the flow of the mobile application and includes all of the menus and screens from part B. [In-Progress]
    // E:SCREENSHOTS: The screenshots provided demonstrate the creation of a deployment package. [In-Progress] - Take a picture of the signed apk
    // F1:MOBILE APPLICATION DEVELOPMENT: The explanation of how the application would be different if developed for a tablet includes both an accurate discussion of both fragments and layouts. []
    // F2:OPERATING SYSTEM: The response identifies both the minimum and the target SDK. []
    // F3:DESCRIPTION OF CHALLENGES: The response describes challenges faced, specifically during the development of the application. []
    // F4:SOLUTIONS TO CHALLENGES: The description of how each challenge was overcome is related to the challenges listed in F2. []
    // F5:DIFFERENCES: The response discusses the changes that would be made to at least one aspect of the project, were it to be done again, and specifically identifies the changes that would be made. []
    // F6:EMULATORS: The description of the use of emulators includes the pros and cons of using an emulator versus using a development device. []
    // G:APA SOURCES: The submission includes in-text citations and references for content that is quoted, paraphrased, or summarized and demonstrates a consistent application of APA style. [] USA APA style
    // H:PROFESSIONAL COMMUNICATION: Use good grammar. Aka grammerly and punctuation. Don't curse; []

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // This disables the night mode which changes the color the text to be unreadable.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Sets the toolbar defined in the layout as the action bar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Insert the Dummy Data the logic for generation is contained within.
        InsertDummyData();
    }

    //Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void gotoTermView(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, TermView.class);
        startActivity(intent);
    }

    public void gotoMentorView(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, MentorView.class);
        startActivity(intent);
    }

    public void gotoCourseView(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, CourseView.class);
        startActivity(intent);
    }

    public void gotoAssessmentView(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AssessmentView.class);
        startActivity(intent);
    }

    private void InsertDummyData() {
        //Script that will insert dummy data into the application.
        Repo repo = new Repo(getApplication());
        Log.d("DUMMYDATA", "The courses in the database before dummy data are " + repo.getCourses());
        // If there are no courses insert some dummy data
        if(repo.getCourses().size() <= 0) {
            //******Courses*******
            // Date Format is MM/dd/YY
            //Values for Status
            // In-Progress -- Completed -- Dropped -- Plan To Take

            //******Courses******
            repo.insertCourse(new Course("Mobile Development", "10/01/2022","10/30/2022","In Progress",1,1, ""));
            repo.insertCourse(new Course("Operating Systems", "06/01/2022", "07/30/2022", "Completed",2,1, ""));
            repo.insertCourse(new Course("Java Fundamentals", "08/01/2022", "08/30/2022", "Completed",3,1, ""));
            repo.insertCourse(new Course("Javascript Basics", "12/01/2022", "01/30/2022", "Plan To Take",4,1, ""));
            repo.insertCourse(new Course("Design Patterns", "07/01/2022", "07/15/2022", "Dropped",5,1, ""));

            //******Mentors******
            //Should always be at least one or else Course view detailed will throw an error.
            repo.insertMentor(new Mentor("Sarah Conor", "561-123-1991", "ResistSky@gmail.com"));
            repo.insertMentor(new Mentor("Lisa Lisa", "039-123-1987", "HamonMaster@gmail.com"));
            repo.insertMentor(new Mentor("Professor Xavier", "010-603-1963", "XMen@gmail.com"));
            repo.insertMentor(new Mentor("Dan Abramov", "203-898-2085", "DanMov@gmail.com"));
            repo.insertMentor(new Mentor("Robert Martin", "943-185-3814", "DanMov@gmail.com"));
            //******Terms******
            repo.insertTerm(new Term("Spring2021","6/01/2021","12/31/2021"));
            repo.insertTerm(new Term("Fall2022","01//01/2022","5/31/2022"));
            repo.insertTerm(new Term("Spring2022","6/01/2022","12/31/2022"));
            repo.insertTerm(new Term("Fall2023","01//01/2023","5/31/2023"));
            //******Assessments******

            repo.insertAssessment(new Assessment("Android App Dev","Performance","10/15/2022","10/30/2022",1));
            repo.insertAssessment(new Assessment("OSx86 Basics","Objective","06/15/2022","07/30/2022",2));
            repo.insertAssessment(new Assessment("Java App-1","Performance","08/15/2022","08/30/2022",3));
            repo.insertAssessment(new Assessment("ECMAScript Cert.","Objective","12/15/2022","01/30/2022",4));
            repo.insertAssessment(new Assessment("GangOfFour Test","Objective","07/15/2022","08/30/2022",5));

            Log.d("DUMMYCOURSE", "The Dummy courses are " + repo.getCourses());
            Log.d("DUMMYMENTOR", "The Dummy mentors are " + repo.getMentors());
            Log.d("DUMMYASSESSMENT", "The Dummy assessments are " + repo.getAssessments());
        }
    }
}