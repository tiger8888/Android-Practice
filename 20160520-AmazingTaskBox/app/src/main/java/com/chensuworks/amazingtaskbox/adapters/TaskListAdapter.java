/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chensuworks.amazingtaskbox.R;
import com.chensuworks.amazingtaskbox.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();

    private final List<Task> mTasks = new ArrayList<Task>();
    private final Context mContext;

    /**
     * ViewHolder pattern to improve ListView scroll performance.
     *      http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    static class ViewHolder {
        TextView taskName;
        TextView taskDescription;
        ImageView taskState;
    }

    public TaskListAdapter(Context context) {
        mContext = context;
    }

    public void add(Task task) {
        mTasks.add(task);
        notifyDataSetChanged();
    }

    public void clear() {
        mTasks.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Object getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Task task;

        /**
         * The convertView argument is essentially a "ScrapView" as described is Lucas post
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout.
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         *      http://www.javacodegeeks.com/2013/09/android-viewholder-pattern-example.html
         */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.taskName = (TextView) convertView.findViewById(R.id.taskName);
            viewHolder.taskDescription = (TextView) convertView.findViewById(R.id.taskDescription);
            viewHolder.taskState = (ImageView) convertView.findViewById(R.id.taskStateIcon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        task = mTasks.get(position);

        viewHolder.taskName.setText(task.getName());
        viewHolder.taskDescription.setText(task.getDescription());
        switch (task.getState()) {
            case TODO:
                /**
                 * getRsources().getDrawable() is deprecated in API 22.
                 *      http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22
                 */
                viewHolder.taskState.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_pen));
                break;
            case IN_PROGRESS:
                viewHolder.taskState.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_controller));
                break;
            case COMPLETED:
                viewHolder.taskState.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_check));
                break;
            case DROPPED:
                viewHolder.taskState.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_trash));
                break;
            default:
                break;
        }

        return convertView;
    }

}
