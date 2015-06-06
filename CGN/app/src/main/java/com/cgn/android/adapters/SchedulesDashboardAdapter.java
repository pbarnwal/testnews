package com.cgn.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cgn.android.R;
import com.cgn.android.activities.ParentActivity;
import com.cgn.android.models.NewsItem;

import java.util.List;

/**
 * Created by yml on 1/4/15.
 */
public class SchedulesDashboardAdapter extends RecyclerView.Adapter<SchedulesDashboardAdapter.ViewHolder> {

    private static final String TAG = SchedulesDashboardAdapter.class.getSimpleName();

    private ParentActivity mActivity;
    private List<NewsItem> mSchedules;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public SchedulesDashboardAdapter(ParentActivity activity, List<NewsItem> dataSet, AdapterView.OnItemClickListener onItemClickListener) {
        mActivity = activity;
        mSchedules = dataSet;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mSchedules.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int mPosition) {

        viewHolder.title.setText(mSchedules.get(mPosition).getmTitle());
        viewHolder.dept.setText(mSchedules.get(mPosition).getmDept());
        viewHolder.time.setText(mSchedules.get(mPosition).getmTime());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.row_schedule_suggested_event,
                parent, false);
        return new ViewHolder(v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView title, dept, time;

        private ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title_txt);
            dept = (TextView) v.findViewById(R.id.dept_txt);
            time = (TextView) v.findViewById(R.id.time_txt);
        }
    }

    public void setSchedules(List<NewsItem> schedules) {
        mSchedules = schedules;
        notifyDataSetChanged();
    }
    /*public void remove(int position) {
        mSchedules.remove(position);
        notifyItemRemoved(position);
    }*/
}
