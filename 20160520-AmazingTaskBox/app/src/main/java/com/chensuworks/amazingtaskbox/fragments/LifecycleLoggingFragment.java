/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LifecycleLoggingFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "Fragment.onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Fragment.onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Fragment.onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Fragment.onActivityCreated()");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "Fragment.onViewStateRestored()");
    }

    @Override
    public void onStart() {
        super.onStop();
        Log.d(TAG, "Fragment.onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Fragment.onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Fragment.onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Fragment.onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "Fragment.onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Fragment.onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Fragment.onDetach()");
    }

}
