package com.example.android.mygreatuniversity.Utils;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.android.mygreatuniversity.R;

public class Utils {
    //Hides the soft keyboard really awkward focusable behavior with edit Text
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
}
