/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chensuworks.amazingtaskbox.R;

public class DroppedFragment extends LifecycleLoggingFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dropped, container, false);
    }

}
