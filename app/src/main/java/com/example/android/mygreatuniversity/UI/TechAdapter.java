package com.example.android.mygreatuniversity.UI;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mygreatuniversity.Database.Repo;
import com.example.android.mygreatuniversity.Entity.Course;
import com.example.android.mygreatuniversity.Entity.Mentor;
import com.example.android.mygreatuniversity.Entity.TechSupport;
import com.example.android.mygreatuniversity.R;
import com.example.android.mygreatuniversity.Utils.StateManager;

import java.util.List;

public class TechAdapter extends RecyclerView.Adapter<TechAdapter.TechViewHolder> {
    class TechViewHolder extends RecyclerView.ViewHolder {
        private final TableRow techTableRow;
        TextView col1TextView, col2TextView, col3TextView;


        //Constructor
        private TechViewHolder(View view) {
            //calls the parent constructor
            super(view); // of the list item
            techTableRow = view.findViewById(R.id.tableRowTech);

            col1TextView = itemView.findViewById(R.id.recyclerCol1);
            col2TextView = itemView.findViewById(R.id.recyclerCol2);
            col3TextView = itemView.findViewById(R.id.recyclerCol3);
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
        //This is the name of the file of the resource not the id for the table row
        View itemView = mInflater.inflate(R.layout.table_row_item, parent, false);
        return new TechViewHolder(itemView);
    }

    //This is where you define the view content
    @Override
    public void onBindViewHolder(@NonNull TechAdapter.TechViewHolder holder, int position) {
        if (mTechs != null) {
            TechSupport curTech = mTechs.get(position);
            String name = curTech.getName();
            String title = curTech.getJobTitle();
            String avail = curTech.getAvailability();
            holder.col1TextView.setText(name);
            holder.col2TextView.setText(title);
            holder.col3TextView.setText(avail);
        } else {
            holder.col1TextView.setText("No Data");
            holder.col2TextView.setText("No Data");
            holder.col3TextView.setText("No Data");
        }
    }

    @Override
    public int getItemCount() {
        //Could return the length like in the android example on android resources.
        //But the size() will work even if the List length is zero.
        if(mTechs != null) {
            return mTechs.size();
        } else return 0;
    }
    //This method tells the activity which items to show.
    public void setTechs(List<TechSupport> techs) {
        Log.d("adapter", "attempting to set techs: " + techs);
        mTechs = techs;
        notifyDataSetChanged();
    }
}
