package com.example.android.mygreatuniversity.UI;

import android.content.Context;
import android.graphics.Color;
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
        //mentorTextView.setTextColor(Color.BLACK); not sure i need this
        // Set the text to the current position in the array and call the getName method
        mentorTextView.setText(mentorArray[position].getName());
        // Return the textView
        return mentorTextView;
    }
}
