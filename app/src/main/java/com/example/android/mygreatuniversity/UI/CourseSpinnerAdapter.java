package com.example.android.mygreatuniversity.UI;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;

public class CourseSpinnerAdapter extends ArrayAdapter {
    //FIELDS
    private Context context;
    private Course[] courseArray;
    //CONSTRUCTOR
    public CourseSpinnerAdapter(Context context, int resId, Course[] courseArray) {
        super(context, resId, courseArray);
        this.context = context;
        this.courseArray = courseArray;
    }

    @Override
    public int getCount(){
        return courseArray.length;
    }

    @Override
    public Course getItem(int pos){
        return courseArray[pos];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create a text view
        TextView courseTextView = (TextView) super.getView(position, convertView, parent);
        // Set the text to the current position in the array and call the getName method
        courseTextView.setText(courseArray[position].getTitle());
        // Return the textView
        return courseTextView;
    }

    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int pos, View convertView,
                                ViewGroup parent) {
        TextView courseTextView = (TextView) super.getDropDownView(pos, convertView, parent);
        //courseTextView.setTextColor(Color.BLACK);
        //This should call the getName method on the mentor object
        //But I im not sure these methods ever get called.
        courseTextView.setText(courseArray[pos].getTitle());
        return courseTextView;
    }

    public Course[] returnValuesAsArray (){
        return courseArray;
    }
}
