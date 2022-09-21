package com.example.android.mygreatuniversity.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {
    class MentorViewHolder extends RecyclerView.ViewHolder {
        private final TextView mentorItemView;

        //Constructor
        private MentorViewHolder(View itemView) {
            //calls the parent constructor
            super(itemView); // of the list item
            mentorItemView = itemView.findViewById(R.id.mentorListItemTextView);
            //This is where you put your onClickListener inside the Constructor for each
            // Course List Item
            mentorItemView.setOnClickListener(v -> {
                //either show a more detailed screen of the course here
                //Or show this in the box below on the course screen.
                int pos = getAdapterPosition();
                //The current item on the list that is sent to the adapter. onClick()
                final Mentor curMentor = mMentors.get(pos);

                //TODO have to create this class in order for the adapter to work
                // Cant compile this code to test it until that class is built.

                //Create the Intent that will pass data to the Course Detailed View
                Intent intent = new Intent(context, MentorViewDetailed.class);

                //TODO verify via tha app inspection that these intent values match column
                // database names

                intent.putExtra("id", curMentor.getMentorID());
                intent.putExtra("name", curMentor.getName());
                intent.putExtra("phone", curMentor.getPhoneNumber());
                intent.putExtra("email" , curMentor.getEmail());
                //Go to the next screen in this case courseViewDetailed
                context.startActivity(intent);
            });
        }
    }
    private List<Mentor> mMentors;
    private final Context context;
    private final LayoutInflater mInflater;
    //CourseAdapter constructor given a context
    public MentorAdapter(Context context) {
        Log.d("adapter", "The Course adapter has been started");
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MentorAdapter.MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.mentor_list_item, parent, false);
        return new MentorViewHolder(itemView);
    }

    //This is where you define the view content
    @Override
    public void onBindViewHolder(@NonNull MentorAdapter.MentorViewHolder holder, int position) {
        if(mMentors != null) {
            Mentor current = mMentors.get(position);
            String name = current.getName();
            holder.mentorItemView.setText(name);
        } else {
            holder.mentorItemView.setText("No name!");
        }
    }

    @Override
    public int getItemCount() {
        //Could return the length like in the android example on android resources.
        //But the size() will work even if the List length is zero.
        if(mMentors != null) {
            return mMentors.size();
        } else return 0;
    }
    //This method tells the activity which items to show.
    public void setMentors(List<Mentor> mentors) {
        Log.d("adapter", "attempting to set mentors: " + mentors);
        mMentors = mentors;
        //Notifies any observers if the dataset has changed
        notifyDataSetChanged();
    }
}
