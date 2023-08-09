package com.example.android.mygreatuniversity.UI;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Entity.MentorAide;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class AideAdapter extends RecyclerView.Adapter<AideAdapter.AideViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_DATA = 1;

    class AideViewHolder extends RecyclerView.ViewHolder {
        private final TableRow aideTableRow;
        private final TableRow aideHeader;

        //private final TableLayout techTableLayout;

        TextView col1AideHeader, col2AideHeader, col3AideHeader, col4AideHeader, col5AideHeader;
        TextView col1AideTextView, col2AideTextView, col3AideTextView, col4AideTextView, col5AideTextView;

        //Constructor
        private AideViewHolder(View view) {
            //Calls the parent constructor
            super(view); // Of the View being passed in.
            //Define the Views to inflate to inflate could separate this into another adapter class
            aideHeader = view.findViewById(R.id.tableRowAideHeader);
            aideTableRow = view.findViewById(R.id.tableRowAide);
            //techTableLayout = view.findViewById(R.id.table)
            //Header for Aides
            col1AideHeader = itemView.findViewById(R.id.headerAideCol1);
            col2AideHeader = itemView.findViewById(R.id.headerAideCol2);
            col3AideHeader = itemView.findViewById(R.id.headerAideCol3);
            col4AideHeader = itemView.findViewById(R.id.headerAideCol4);
            col5AideHeader = itemView.findViewById(R.id.headerAideCol5);
            //Columns for the body of data for Aides
            col1AideTextView = itemView.findViewById(R.id.recyclerAideCol1);
            col2AideTextView = itemView.findViewById(R.id.recyclerAideCol2);
            col3AideTextView = itemView.findViewById(R.id.recyclerAideCol3);
            col4AideTextView = itemView.findViewById(R.id.recyclerAideCol4);
            col5AideTextView = itemView.findViewById(R.id.recyclerAideCol5);
        }
    }

    private List<MentorAide> mAides;
    private final Context context;
    private final LayoutInflater mInflater;

    //CourseAdapter constructor given a context
    public AideAdapter(Context context) {
        Log.d("adapter", "The Tech adapter has been started");
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AideAdapter.AideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //The parent will decide what doe inflating
        //Linear Layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_HEADER) {
            View headerView = inflater.inflate(R.layout.aide_header, parent, false);
            return new AideViewHolder(headerView);
        } else {
            View dataView = inflater.inflate(R.layout.table_row_aide, parent, false);
            return new AideViewHolder(dataView);
        }
    }


    //This is where you define the view content
    @Override
    public void onBindViewHolder(@NonNull AideAdapter.AideViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            // Bind the header Text for the Columns here
            holder.col1AideHeader.setText("Name");
            holder.col2AideHeader.setText("Title");
            holder.col3AideHeader.setText("Availability");
            holder.col4AideHeader.setText("Subjects");
            holder.col5AideHeader.setText("Email");
        } else {
            MentorAide curAide = mAides.get(position - 1); // Subtract 1 for the header
            //Call the Method on the Object to get col data.
            String name = curAide.getName();
            String title = curAide.getJobTitle();
            String avail = curAide.getAvailability();
            String subjects = curAide.getSubjects();
            String email = curAide.getEmail();
            //Bind the data for the Data Columns
            holder.col1AideTextView.setText(name);
            holder.col2AideTextView.setText(title);
            holder.col3AideTextView.setText(avail);
            holder.col4AideTextView.setText(subjects);
            holder.col5AideTextView.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        if (mAides != null) {
            //The Plus one is for the Header
            return mAides.size() + 1;
        } else return 0;
    }

    //Need this to position the Header correctly
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_DATA;
    }

    //This method tells the activity which items to show.
    public void setAides(List<MentorAide> mAides) {
        Log.d("adapter", "attempting to set mentor Aides: " + mAides);
        this.mAides = mAides;
        notifyDataSetChanged();
    }
}

