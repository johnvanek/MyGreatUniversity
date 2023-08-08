package com.example.android.mygreatuniversity.UI;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.TechSupport;
import com.example.android.mygreatuniversity.R;

import java.util.List;

public class TechAdapter extends RecyclerView.Adapter<TechAdapter.TechViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_DATA = 1;

    class TechViewHolder extends RecyclerView.ViewHolder {
        private final TableRow techTableRow;
        private final TableRow myTechHeader;

        //private final TableLayout techTableLayout;

        TextView col1TechHeader, col2TechHeader, col3TechHeader;
        TextView col1TechTextView, col2TechTextView, col3TechTextView;

        //Constructor
        private TechViewHolder(View view) {
            //Calls the parent constructor
            super(view); // Of the View being passed in.
            //Define the Views to inflate to inflate could separate this into another adapter class
            myTechHeader = view.findViewById(R.id.tableRowTechHeader);
            techTableRow = view.findViewById(R.id.tableRowTech);
            //techTableLayout = view.findViewById(R.id.table)
            //Header for Tech
            col1TechHeader = itemView.findViewById(R.id.headerTechCol1);
            col2TechHeader = itemView.findViewById(R.id.headerTechCol2);
            col3TechHeader = itemView.findViewById(R.id.headerTechCol3);
            //Columns for the body of data for Tech
            col1TechTextView = itemView.findViewById(R.id.recyclerCol1);
            col2TechTextView = itemView.findViewById(R.id.recyclerCol2);
            col3TechTextView = itemView.findViewById(R.id.recyclerCol3);
        }
    }

    private List<Course> mCourses;
    private List<TechSupport> mTechs;
    private final Context context;
    private final LayoutInflater mInflater;

    //CourseAdapter constructor given a context
    public TechAdapter(Context context) {
        Log.d("adapter", "The Tech adapter has been started");
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TechAdapter.TechViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //The parent will decide what doe inflating
        //Linear Layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_HEADER) {
            View headerView = inflater.inflate(R.layout.tech_header, parent, false);
            return new TechViewHolder(headerView);
        } else {
            View dataView = inflater.inflate(R.layout.table_row_item, parent, false);
            return new TechViewHolder(dataView);
        }
        //This is the name of the file of the resource not the id for the table row
        //View itemView = mInflater.inflate(R.layout.table_row_item, parent, false);
        //return new TechViewHolder(itemView);
    }


    //This is where you define the view content
    @Override
    public void onBindViewHolder(@NonNull TechAdapter.TechViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            // Bind the header Text for the Columns here
            holder.col1TechHeader.setText("Name");
            holder.col2TechHeader.setText("Title");
            holder.col3TechHeader.setText("Availability");
        } else {
            TechSupport curTech = mTechs.get(position - 1); // Subtract 1 for the header
            //Call the Method on the Object to get col data.
            String name = curTech.getName();
            String title = curTech.getJobTitle();
            String avail = curTech.getAvailability();
            //Bind the data for the Data Columns
            holder.col1TechTextView.setText(name);
            holder.col2TechTextView.setText(title);
            holder.col3TechTextView.setText(avail);
        }
    }

    @Override
    public int getItemCount() {
        if (mTechs != null) {
            //The Plus one is for the Header
            return mTechs.size() + 1;
        } else return 0;
    }


    //Need this to position the Header correctly
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_DATA;
    }


    //This method tells the activity which items to show.
    public void setTechs(List<TechSupport> techs) {
        Log.d("adapter", "attempting to set techs: " + techs);
        mTechs = techs;
        notifyDataSetChanged();
    }
}
