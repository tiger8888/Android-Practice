package com.chensuworks.database.pettracker.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.chensuworks.database.pettracker.database.PetTrackerDatabaseHelper;

public class PetTrackerActivity extends Activity {

    protected PetTrackerDatabaseHelper mDatabaseHelper = null;
    protected Cursor mCursor = null;
    protected SQLiteDatabase mDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseHelper = new PetTrackerDatabaseHelper(getApplicationContext());
        mDB = mDatabaseHelper.getWritableDatabase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDatabaseHelper != null) {
            mDatabaseHelper.close();
        }

        if (mDB != null) {
            mDB.close();
        }
    }

}
