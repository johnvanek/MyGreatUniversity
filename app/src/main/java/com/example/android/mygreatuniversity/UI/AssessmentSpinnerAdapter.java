package com.example.android.mygreatuniversity.UI;

import android.content.Context;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;

import java.util.List;


public class AssessmentSpinnerAdapter extends ArrayAdapter {
    //FIELDS
    private Context context;
    private Assessment[] assessmentArray;
    //CONSTRUCTOR
    public AssessmentSpinnerAdapter(Context context, int resId, Assessment[] mentorArray) {
        super(context, resId, mentorArray);
        this.context = context;
        this.assessmentArray = mentorArray;
    }

    @Override
    public int getCount(){
        return assessmentArray.length;
    }

    @Override
    public Assessment getItem(int pos){
        return assessmentArray[pos];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create a text view
        TextView assessmentTextView = (TextView) super.getView(position, convertView, parent);
        // Set the text to the current position in the array and get the title
        assessmentTextView.setText(assessmentArray[position].getTitle());
        // Return the textView
        return assessmentTextView;
    }

    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int pos, View convertView,
                                ViewGroup parent) {
        TextView assessmentTextView = (TextView) super.getDropDownView(pos, convertView, parent);
        //assessmentTextView.setTextColor(Color.BLACK);
        //This should call the getName method on the assessment object
        //But I im not sure these methods ever get called.
        assessmentTextView.setText(assessmentArray[pos].getTitle());
        return assessmentTextView;
    }

    public Assessment[] returnValuesAsArray (){
        return assessmentArray;
    }
}
