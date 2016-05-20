/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import java.util.HashMap;

public class RetainedFragmentManager {

    private RetainedFragment mRetainedFragment;
    private FragmentManager mFragmentManager;
    private String mRetainedFragmentTag;

    public RetainedFragmentManager(Activity activity, String retainedFragmentTag) {
        mFragmentManager = activity.getFragmentManager();
        mRetainedFragmentTag = retainedFragmentTag;
    }

    public boolean firstTimeIn() {
        mRetainedFragment = (RetainedFragment) mFragmentManager.findFragmentByTag(mRetainedFragmentTag);

        if (mRetainedFragment != null) {
            return false;

        } else {
            mRetainedFragment = new RetainedFragment();
            mFragmentManager.beginTransaction().add(mRetainedFragment, mRetainedFragmentTag).commit();
            return true;
        }
    }

    public void put(String key, Object object) {
        mRetainedFragment.put(key, object);
    }

    public void put(Object object) {
        put(object.getClass().getName(), object);
    }

    public <T> T get(String key) {
        return (T) mRetainedFragment.get(key);
    }


    public static class RetainedFragment extends LifecycleLoggingFragment {

        private final String TAG = getClass().getSimpleName();
        private HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        public void put(String key, Object object) {
            mData.put(key, object);
        }

        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }

}
