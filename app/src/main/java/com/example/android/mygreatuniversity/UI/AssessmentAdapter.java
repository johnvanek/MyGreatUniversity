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
import com.example.android.mygreatuniversity.Entity.Assessment;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        //Constructor
        private AssessmentViewHolder(View itemView) {
            // Calls the parent constructor
            super(itemView); // of the list item
            assessmentItemView = itemView.findViewById(R.id.assessmentListItemTextView);
            // This is where you put your onClickListener inside the Constructor for each
            // Course List Item
            assessmentItemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                //The current item on the list that is sent to the adapter. onClick()
                final Assessment curAssessment = mAssessments.get(pos);
                //Create the Intent that will pass data to the Course Detailed View
                Intent intent = new Intent(context, AssessmentViewDetailed.class);

                intent.putExtra("assessmentID", curAssessment.getAssessmentID());
                intent.putExtra("title", curAssessment.getTitle());
                intent.putExtra("startDate", curAssessment.getStartDate());
                intent.putExtra("endDate" , curAssessment.getEndDate());
                intent.putExtra("courseID", curAssessment.getAssessmentCourseID());
                intent.putExtra("type", curAssessment.getType());
                Repo repo = new Repo((Application) context.getApplicationContext());
                //Go to the next screen in this case courseViewDetailed
                context.startActivity(intent);
            });
        }
    }
    //add
    private List<Assessment> mAssessments;
    //private List<Mentor> mMentors;
    private final Context context;
    private final LayoutInflater mInflater;

    //AssessmentAdapter constructor given a context
    public AssessmentAdapter(Context context) {
        Log.d("adapter", "The Assessment adapter has been started");
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    //This is where you define the view content
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String title = current.getTitle();
            holder.assessmentItemView.setText(title);
        } else {
            holder.assessmentItemView.setText("No title!");
        }
    }

    @Override
    public int getItemCount() {
        if(mAssessments != null) {
            return mAssessments.size();
        } else return 0;
    }
    //This method tells the activity which items to show.
    public void setAssessments(List<Assessment> assessments) {
        Log.d("adapter", "attempting to set assessments: " + assessments);
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
