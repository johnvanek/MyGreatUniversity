package com.example.android.mygreatuniversity.UI;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;

import java.util.List;

public class TermCourseAdapter extends RecyclerView.Adapter<TermCourseAdapter.TermCourseViewHolder>{
    class TermCourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView termCourseItemView;

        //Constructor
        private TermCourseViewHolder(View itemView) {
            //calls the parent constructor

            super(itemView); // of the list item
            termCourseItemView = itemView.findViewById(R.id.termCoursesListItemTextView);
            //This is where you put your onClickListener inside the Constructor for each
            // Course List Item
            termCourseItemView.setOnClickListener(v -> {
                //either show a more detailed screen of the course here
                //Or show this in the box below on the course screen.
                int pos = getAdapterPosition();
                //The current item on the list that is sent to the adapter. onClick()
                final Course curTermCourse = mTermCourses.get(pos);
                // call repo
                Intent intent = new Intent(context, CourseViewDetailed.class);
                Repo repo = new Repo((Application) context.getApplicationContext());
                //Give some extra data to the intent for the Course
                intent.putExtra("id", curTermCourse.getCourseID());
                intent.putExtra("title", curTermCourse.getTitle());
                intent.putExtra("startDate", curTermCourse.getStartDate());
                intent.putExtra("endDate" , curTermCourse.getEndDate());
                intent.putExtra("status", curTermCourse.getStatus());
                //Also give some information for the mentor
                Mentor courseMentor = repo.findMentorById(curTermCourse.getCourseMentorId());
                if(courseMentor == null){
                    //default to the first course mentor
                    courseMentor = mMentors.get(0);
                }

                //Put the courseMentor information in the intent as well
                intent.putExtra("mentorId", courseMentor.getMentorID());
                intent.putExtra("mentorName", courseMentor.getName());
                intent.putExtra("mentorPhone",courseMentor.getPhoneNumber());
                intent.putExtra("mentorEmail", courseMentor.getEmail());
                //Put the associated Term information in
                intent.putExtra("termID", curTermCourse.getTermID());
                //Let the StateManager know that we are coming from the Term Detailed Activity
                StateManager.setArrivedToCourseFromTermView(true);
                //Go to the next screen in this case TermViewDetailed
                context.startActivity(intent);
            });
        }
    }
    private List<Course> mTermCourses;
    private List<Mentor> mMentors;
    private final Context context;
    private final LayoutInflater mInflater;

    //TermAdapter constructor given a context
    public TermCourseAdapter(Context context) {
        Log.d("adapter", "The Term-Course adapter has been started");
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermCourseAdapter.TermCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_course_item, parent, false);
        return new TermCourseAdapter.TermCourseViewHolder(itemView);
    }

    //This is where you define the view content
    @Override
    public void onBindViewHolder(@NonNull TermCourseAdapter.TermCourseViewHolder holder, int position) {
        if(mTermCourses != null) {
            Course current = mTermCourses.get(position);
            String title = current.getTitle();
            holder.termCourseItemView.setText(title);
        } else {
            holder.termCourseItemView.setText("No title!");
        }
    }

    @Override
    public int getItemCount() {
        //Could return the length like in the android example on android resources.
        //But the size() will work even if the List length is zero.
        if(mTermCourses != null) {
            return mTermCourses.size();
        } else return 0;
    }
    //This method tells the activity which items to show.
    public void setTermCourses(List<Course> termCourses) {
        Log.d("adapter", "attempting to set term courses: " + termCourses);
        mTermCourses = termCourses;
        notifyDataSetChanged();
    }

    public void setMentors(List<Mentor> mentors) {
        Log.d("adapter", "attempting to set mentors: " + mentors);
        mMentors = mentors;
        notifyDataSetChanged();
    }
}
