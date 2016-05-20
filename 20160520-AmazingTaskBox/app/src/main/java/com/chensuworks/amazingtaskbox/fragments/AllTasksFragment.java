/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chensuworks.amazingtaskbox.R;
import com.chensuworks.amazingtaskbox.adapters.TaskListAdapter;
import com.chensuworks.amazingtaskbox.databases.TaskListDatabase;
import com.chensuworks.amazingtaskbox.databases.TaskListDatabaseHelper;
import com.chensuworks.amazingtaskbox.models.Task;

/**
 * Custom ListView tutorial:
 *      http://www.codelearn.org/android-tutorial/android-listview
 */
public class AllTasksFragment extends LifecycleLoggingFragment {

    private final String TAG = getClass().getSimpleName();

    private Context mContext;
    private Activity mActivity;
    private View mView;
    private FloatingActionButton fab;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContext = activity.getApplicationContext();
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_all_tasks, container, false);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        TaskListDatabaseHelper databaseHelper = new TaskListDatabaseHelper(mContext);

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        generateMockTasksToDB(database);
        database.close();


        final TaskListAdapter taskListAdapter = new TaskListAdapter(mContext);
        //generateMockTasks(taskListAdapter);

        database = databaseHelper.getReadableDatabase();
        queryTasksFromDB(database, taskListAdapter);
        database.close();


        ListView allTasksListView = (ListView) mView.findViewById(R.id.allTasksListView);
        allTasksListView.setAdapter(taskListAdapter);
        allTasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) taskListAdapter.getItem(position);
                Toast.makeText(mContext, task.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mActivity.findViewById(android.R.id.content), "Are you sure you want to create a new task?", Snackbar.LENGTH_LONG)
                        .setAction("YES", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(mContext, "Continue creating new task...", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(Color.WHITE)
                        .show();

            }
        });
    }

    private void generateMockTasks(TaskListAdapter adapter) {
        adapter.add(new Task("Try Android Studio", "follow tutorial"));
        adapter.add(new Task("KeyHandler refactoring", "work with Anitha"));
        adapter.add(new Task("RTC first load", "retain selection and scroll issue"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
        adapter.add(new Task("MOCK", "this is a mock task"));
    }

    private void generateMockTasksToDB(SQLiteDatabase database) {
        insertMockTask(database, "DB - Try Android Studio", "follow tutorial", "2015-09-06 13:43:24", "2015-09-06 13:43:24", "TODO");
        insertMockTask(database, "DB - KeyHandler refactoring", "work with Anitha", "2015-09-06 13:43:24", "2015-09-06 13:43:24", "TODO");
        insertMockTask(database, "DB - RTC first load", "retain selection and scroll issue", "2015-09-06 13:43:24", "2015-09-06 13:43:24", "TODO");
    }

    private long insertMockTask(SQLiteDatabase database, String name, String description,
                                String createdDate, String modifiedDate, String state) {
        ContentValues values = new ContentValues();

        values.put(TaskListDatabase.TasksTable.NAME, name);
        values.put(TaskListDatabase.TasksTable.DESCRIPTION, description);
        values.put(TaskListDatabase.TasksTable.CREATED_DATE, createdDate);
        values.put(TaskListDatabase.TasksTable.MODIFIED_DATE, modifiedDate);
        values.put(TaskListDatabase.TasksTable.STATE, state);

        return database.insert(TaskListDatabase.TasksTable.TASKS_TABLE_NAME, null, values);
    }

    private void queryTasksFromDB(SQLiteDatabase database, TaskListAdapter taskListAdapter) {
        Cursor cursor = database.query(TaskListDatabase.TasksTable.TASKS_TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            taskListAdapter.add(new Task(cursor.getString(1), cursor.getString(2)));
        }

        cursor.close();
    }

}
