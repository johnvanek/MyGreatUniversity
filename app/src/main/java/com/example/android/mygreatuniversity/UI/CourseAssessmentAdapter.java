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
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CourseAssessmentAdapter extends RecyclerView.Adapter<CourseAssessmentAdapter.CourseAssessmentViewHolder>{
    class CourseAssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseAssessmentItemView;
        Repo repo = new Repo(StateManager.getApp());

        //Constructor
        private CourseAssessmentViewHolder(View itemView) {
            //calls the parent constructor
            super(itemView); // of the list item
            courseAssessmentItemView = itemView.findViewById(R.id.courseAssessmentListItem);
            courseAssessmentItemView.setOnClickListener(v -> {
                //Could change this to be the logic for a course assessment removal
                Snackbar snackbar = Snackbar.make(courseAssessmentItemView,"Remove Assessment From Course?",Snackbar.LENGTH_LONG);
                snackbar.setDuration(5000);
                snackbar.setTextMaxLines(30);

                snackbar.setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getAdapterPosition();
                        //The current item on the list that is sent to the adapter. onClick()
                        final Assessment curCourseAssessment = mCourseAssessments.get(pos);

                        Repo repo = new Repo((Application) context.getApplicationContext());
                        //update by setting this to zero it makes it so that is does not show up again
                        //It is not delete but is removed from the course.
                        curCourseAssessment.setAssessmentCourseID(0);
                        repo.updateAssessment(curCourseAssessment);
                        //In order to redraw the adapter I have to send to user back to the
                        // previous screen cant figure out another way.

                        //TODO need to write code here to figure out where they came from
                        // If feeling to tight on time send back to Main Activity.
                        Intent intent = new Intent(context, MainActivity.class);
                        //Create a toast here if possible
                        Toast.makeText(context,"Assessment Removed From Course",Toast.LENGTH_SHORT).show();
                        context.startActivity(intent);
                    }
                });
                snackbar.show();
            });

            courseAssessmentItemView.setOnLongClickListener(v -> {
                //either show a more detailed screen of the course here
                //Or show this in the box below on the course screen.
                int pos = getAdapterPosition();
                //The current item on the list that is sent to the adapter. onClick()
                final Assessment curCourseAssessment = mCourseAssessments.get(pos);
                // call repo
                Intent intent = new Intent(context, AssessmentViewDetailed.class);
                Repo repo = new Repo((Application) context.getApplicationContext());
                //Give some extra data to the intent for the Course
                intent.putExtra("assessmentID", curCourseAssessment.getAssessmentID());
                intent.putExtra("courseID", curCourseAssessment.getAssessmentCourseID());
                intent.putExtra("title", curCourseAssessment.getTitle());
                intent.putExtra("startDate", curCourseAssessment.getStartDate());
                intent.putExtra("endDate", curCourseAssessment.getEndDate());
                intent.putExtra("type", curCourseAssessment.getType());

                //StateManager.setArrivedToCourseFromTermView(true);
                //Go to the next screen in this case TermViewDetailed
                context.startActivity(intent);

                return false;
            });
        }

    }
    private List<Assessment> mCourseAssessments;
    private List<Mentor> mMentors;
    private final Context context;
    private final LayoutInflater mInflater;

    //TermAdapter constructor given a context
    public CourseAssessmentAdapter(Context context) {
        Log.d("adapter", "The Course Assessment adapter has been started");
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAssessmentAdapter.CourseAssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_assessment_item, parent, false);
        return new CourseAssessmentAdapter.CourseAssessmentViewHolder(itemView);
    }
    //This is where you define the view content
    @Override
    public void onBindViewHolder(@NonNull CourseAssessmentAdapter.CourseAssessmentViewHolder holder, int position) {
        if(mCourseAssessments != null) {
            Assessment current = mCourseAssessments.get(position);
            String title = current.getTitle();
            holder.courseAssessmentItemView.setText(title);
        } else {
            holder.courseAssessmentItemView.setText("No title!");
        }
    }

    @Override
    public int getItemCount() {
        //Could return the length like in the android example on android resources.
        //But the size() will work even if the List length is zero.
        if(mCourseAssessments != null) {
            return mCourseAssessments.size();
        } else return 0;
    }

    public void setCourseAssessments(List<Assessment> courseAssessments) {
        Log.d("adapter", "attempting to set course-assessments: " + courseAssessments);
        mCourseAssessments = courseAssessments;
        notifyDataSetChanged();
    }
}
