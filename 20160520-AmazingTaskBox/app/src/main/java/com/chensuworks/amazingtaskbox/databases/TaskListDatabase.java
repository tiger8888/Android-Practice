/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.databases;

import android.provider.BaseColumns;

public class TaskListDatabase {

    public static final class TasksTable implements BaseColumns {
        private TasksTable() {}

        public static final String TASKS_TABLE_NAME = "tasks";

        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String CREATED_DATE = "created_date";
        public static final String MODIFIED_DATE = "modified_date";
        public static final String STATE = "state";
    }
}
