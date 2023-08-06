package com.example.android.mygreatuniversity.Utils;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.android.mygreatuniversity.Entity.TechSupport;
import com.example.android.mygreatuniversity.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utils {
    //Hides the soft keyboard really awkward focusable behavior with edit Text and layouts
    //No longer needed the better solution if to override the dispatchTouchEvent in the activity
    // Follow the example that is scene in course view detailed

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
    public static int courseStatusPosition(Activity activity, String intentString){
        Resources res = activity.getResources();
        int position = 0;
        int index = 0;
        String[] course_status = res.getStringArray(R.array.course_status_array);
        for (String status: course_status) {
            if(intentString.equals(status)){
                position = index;
                break;
            }
            index++;
        }
        return position;
    }
    public static int assessmentTypePosition(Activity activity, String intentString){
        Resources res = activity.getResources();
        int position = 0;
        int index = 0;
        String[] assessment_type = res.getStringArray(R.array.type_array);
        for (String type: assessment_type) {
            if(intentString.equals(type)){
                position = index;
                break;
            }
            index++;
        }
        return position;
    }

    //De-Stringer Method for availability should also work for subjects in Misc-Faculty
        public static boolean deStringer(String stringList, String targetString) {
            String[] strings = stringList.split(",\\s*");
            //Loop over each day and check if the same
            for (String string : strings) {
                if (string.equalsIgnoreCase(targetString)) {
                    return true; // Day found in the list
                }
            }
            return false; // Day not found in the list
        }

        public static void techSupportTimeStampHelper(TechSupport[] techSupportPersonnel) {
            Calendar calendar = Calendar.getInstance();
            for (TechSupport tech: techSupportPersonnel) {
                //For for Each TechSupport Person
                //Randomize the start Dates
                //Minus between 1 and 5 years and 1 and 5 months
                calendar.add(Calendar.YEAR, -(generateRandomNumberBetween1and5()));
                calendar.add(Calendar.MONTH,-(generateRandomNumberBetween1and5()));
                calendar.add(Calendar.DAY_OF_WEEK_IN_MONTH,-(generateRandomNumberBetween1and12()));
                //Now that this is some what Randomized call the Set Hired Date method on the object.
                long dateHired = calendar.getTimeInMillis();
                tech.setDateHired(dateHired);
            }
        }

    public static int generateRandomNumberBetween1and5() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    public static int generateRandomNumberBetween1and12() {
        Random random = new Random();
        return random.nextInt(12) + 1;
    }
}
