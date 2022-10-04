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

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        //Constructor
        private TermViewHolder(View itemView) {
            //calls the parent constructor
            super(itemView); // of the list item
            termItemView = itemView.findViewById(R.id.termListItemTextView);
            //This is where you put your onClickListener inside the Constructor for each
            // Course List Item
            termItemView.setOnClickListener(v -> {
                //either show a more detailed screen of the course here
                //Or show this in the box below on the course screen.
                int pos = getAdapterPosition();
                //The current item on the list that is sent to the adapter. onClick()
                final Term curTerm = mTerms.get(pos);
                // call repo
                //Mentor courseMentor =
                //Create the Intent that will pass data to the Course Detailed View
                Intent intent = new Intent(context, TermViewDetailed.class);
                //Give some extra data to the intent
                intent.putExtra("id", curTerm.getTermID());
                intent.putExtra("title", curTerm.getTitle());
                intent.putExtra("startDate", curTerm.getStartDate());
                intent.putExtra("endDate" , curTerm.getEndDate());
                //Don't need access to repo currently
                //Repo repo = new Repo((Application) context.getApplicationContext());
                //Sent the intent go to the next activity
                context.startActivity(intent);
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    //CourseAdapter constructor given a context
    public TermAdapter(Context context) {
        Log.d("adapter", "The Course adapter has been started");
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermAdapter.TermViewHolder(itemView);
    }

    //This is where you define the view content
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms != null) {
            Term current = mTerms.get(position);
            String title = current.getTitle();
            holder.termItemView.setText(title);
        } else {
            holder.termItemView.setText("No title!");
        }
    }

    @Override
    public int getItemCount() {
        //Could return the length like in the android example on android resources.
        //But the size() will work even if the List length is zero.
        if(mTerms != null) {
            return mTerms.size();
        } else return 0;
    }
    //This method tells the activity which items to show.
    public void setTerms(List<Term> terms) {
        Log.d("adapter", "attempting to set terms: " + terms);
        mTerms = terms;
        notifyDataSetChanged();
    }
}
