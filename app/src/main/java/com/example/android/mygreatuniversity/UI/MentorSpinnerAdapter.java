package com.example.android.mygreatuniversity.UI;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.mygreatuniversity.Entity.Mentor;

public class MentorSpinnerAdapter extends ArrayAdapter {
    //FIELDS
    private Context context;
    private Mentor[] mentorArray;
    //CONSTRUCTOR
    public MentorSpinnerAdapter(Context context, int resId, Mentor[] mentorArray) {
        super(context, resId, mentorArray);
        this.context = context;
        this. mentorArray = mentorArray;
    }

    @Override
    public int getCount(){
        return mentorArray.length;
    }

    @Override
    public Mentor getItem(int pos){
        return mentorArray[pos];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create a text view
        TextView mentorTextView = (TextView) super.getView(position, convertView, parent);
        // Set the text to the current position in the array and call the getName method
        mentorTextView.setText(mentorArray[position].getName());
        // Return the textView
        return mentorTextView;
    }

    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int pos, View convertView,
                                ViewGroup parent) {
        TextView mentorTextView = (TextView) super.getDropDownView(pos, convertView, parent);
        //mentorTextView.setTextColor(Color.BLACK);
        //This should call the getName method on the mentor object
        //But I im not sure these methods ever get called.
        mentorTextView.setText(mentorArray[pos].getName());
        return mentorTextView;
    }
}
