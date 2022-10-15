package com.example.android.mygreatuniversity.UI;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.Term;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TermCourseAdapter extends RecyclerView.Adapter<TermCourseAdapter.TermCourseViewHolder>{
    class TermCourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView termCourseItemView;
        Repo repo = new Repo(StateManager.getApp());

        //Constructor
        private TermCourseViewHolder(View itemView) {
            //calls the parent constructor

            super(itemView); // of the list item
            termCourseItemView = itemView.findViewById(R.id.termCoursesListItemTextView);
            //TODO got rid of this onClick listener could remove the came from termView Functionality
            // In courseView detailed.

            termCourseItemView.setOnLongClickListener(v -> {
                Snackbar snackbar = Snackbar.make(termCourseItemView,"Remove Course From Term?",Snackbar.LENGTH_LONG);
                snackbar.setDuration(5000);
                snackbar.setTextMaxLines(30);

                snackbar.setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getAdapterPosition();
                        //The current item on the list that is sent to the adapter. onClick()
                        final Course curTermCourse = mTermCourses.get(pos);

                        Repo repo = new Repo((Application) context.getApplicationContext());
                        //update the course setting its adapter to zero
                        curTermCourse.setTermID(0);
                        repo.updateCourse(curTermCourse);
                        //In order to redraw the adapter I have to send to user back to the
                        // previous screen cant figure out another way.
                        Intent intent = new Intent(context, TermView.class);
                        //Create a toast here if possible
                        Toast.makeText(context,"TermCourses Changed",Toast.LENGTH_SHORT).show();
                        context.startActivity(intent);
                    }
                });
                snackbar.show();
                return false;
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
