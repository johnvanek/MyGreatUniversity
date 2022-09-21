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

import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        //Constructor
        private CourseViewHolder(View itemView) {
            //calls the parent constructor
            super(itemView); // of the list item
            courseItemView = itemView.findViewById(R.id.courseListItemTextView);
            //This is where you put your onClickListener inside the Constructor for each
            // Course List Item
            courseItemView.setOnClickListener(v -> {
                //either show a more detailed screen of the course here
                //Or show this in the box below on the course screen.
                int pos = getAdapterPosition();
                //The current item on the list that is sent to the adapter. onClick()
                final Course curCourse = mCourses.get(pos);

                //Create the Intent that will pass data to the Course Detailed View
                Intent intent = new Intent(context, CourseViewDetailed.class);
                //Give some extra data to the intent
                intent.putExtra("id", curCourse.getCourseID());
                intent.putExtra("title", curCourse.getTitle());
                intent.putExtra("startDate", curCourse.getStartDate());
                intent.putExtra("endDate" , curCourse.getEndDate());
                intent.putExtra("status", curCourse.getStatus());
                //Go to the next screen in this case courseViewDetailed
                //TODO add mentor data to the intent put
                context.startActivity(intent);
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    //CourseAdapter constructor given a context
    public CourseAdapter(Context context) {
        Log.d("adapter", "The Course adapter has been started");
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    //This is where you define the view content
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses != null) {
            Course current = mCourses.get(position);
            String title = current.getTitle();
            holder.courseItemView.setText(title);
        } else {
            holder.courseItemView.setText("No title!");
        }
    }

    @Override
    public int getItemCount() {
        //Could return the length like in the android example on android resources.
        //But the size() will work even if the List length is zero.
        if(mCourses != null) {
            return mCourses.size();
        } else return 0;
    }
    //This method tells the activity which items to show.
    public void setCourses(List<Course> courses) {
        Log.d("adapter", "attempting to set courses: " + courses);
        mCourses = courses;
        notifyDataSetChanged();
    }
}
