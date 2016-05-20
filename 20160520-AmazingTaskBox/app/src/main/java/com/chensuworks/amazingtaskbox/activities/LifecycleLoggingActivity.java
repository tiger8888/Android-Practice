/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LifecycleLoggingActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Log.d(TAG, "Activity.onCreate(): activity re-created from savedInstanceState.");
        } else {
            Log.d(TAG, "Activity.onCreate(): activity created anew.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Activity.onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Activity.onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Activity.onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Activity.onStop()");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "Activity.onRestart()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity.onDestroy()");
    }

}
