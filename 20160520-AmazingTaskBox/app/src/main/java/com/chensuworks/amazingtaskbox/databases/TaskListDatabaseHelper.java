/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskListDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_list.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_TASKS =
            "CREATE TABLE " + TaskListDatabase.TasksTable.TASKS_TABLE_NAME + " (" +
                    TaskListDatabase.TasksTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TaskListDatabase.TasksTable.NAME + " TEXT," +
                    TaskListDatabase.TasksTable.DESCRIPTION + " TEXT," +
                    TaskListDatabase.TasksTable.CREATED_DATE + " TEXT," +
                    TaskListDatabase.TasksTable.MODIFIED_DATE + " TEXT," +
                    TaskListDatabase.TasksTable.STATE + " TEXT" +
            ")";

    public TaskListDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Housekeeping to move application data during an upgrade of schema versions.
        // Move or delete
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
