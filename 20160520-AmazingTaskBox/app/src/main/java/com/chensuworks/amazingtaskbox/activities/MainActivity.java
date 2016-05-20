/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chensuworks.amazingtaskbox.R;
import com.chensuworks.amazingtaskbox.fragments.AllTasksFragment;
import com.chensuworks.amazingtaskbox.fragments.CompletedFragment;
import com.chensuworks.amazingtaskbox.fragments.DroppedFragment;
import com.chensuworks.amazingtaskbox.fragments.InProgressFragment;
import com.chensuworks.amazingtaskbox.fragments.RetainedFragmentManager;
import com.chensuworks.amazingtaskbox.fragments.ToDoFragment;
import com.chensuworks.amazingtaskbox.utils.Constants;

public class MainActivity extends LifecycleLoggingActivity {

    private final String TAG = getClass().getSimpleName();

    private RetainedFragmentManager mRetainedFragmentManager = new RetainedFragmentManager(this, TAG);

    private Toolbar mToolbar;
    private Spinner mSpinner;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (mRetainedFragmentManager.firstTimeIn()) {
            mRetainedFragmentManager.put("key", "value");
            Log.d(TAG, "Activity created for the first time!");
        } else {
            Log.d(TAG, "Activity not created for the first time, retained value = \"" + mRetainedFragmentManager.get("key") + "\"");
        }

        mFragmentManager = getFragmentManager();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSpinner = (Spinner) findViewById(R.id.toolbarSpinner);
        mSpinner.setAdapter(new CustomArrayAdapter(this, R.layout.toolbar_spinner_item, Constants.tabs));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "position = " + position, Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 0:
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.fragmentContainer, new AllTasksFragment(), Constants.tabs[0]);
                        mFragmentTransaction.commit();
                        break;
                    case 1:
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.fragmentContainer, new ToDoFragment(), Constants.tabs[1]);
                        mFragmentTransaction.commit();
                        break;
                    case 2:
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.fragmentContainer, new InProgressFragment(), Constants.tabs[2]);
                        mFragmentTransaction.commit();
                        break;
                    case 3:
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.fragmentContainer, new CompletedFragment(), Constants.tabs[3]);
                        mFragmentTransaction.commit();
                        break;
                    case 4:
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.fragmentContainer, new DroppedFragment(), Constants.tabs[4]);
                        mFragmentTransaction.commit();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    public void optionSettingsClicked(MenuItem menuItem) {
        Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_SHORT).show();
    }

    public void optionHelpClicked(MenuItem menuItem) {
        Toast.makeText(getApplicationContext(), "help", Toast.LENGTH_SHORT).show();
    }

    public class CustomArrayAdapter extends ArrayAdapter<String> {
        private int resourceId;

        public CustomArrayAdapter(Context context, int resourceId, String[] strings) {
            super(context, resourceId, strings);

            this.resourceId = resourceId;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View spinnerView = inflater.inflate(resourceId, parent, false);

            TextView tabText = (TextView) spinnerView.findViewById(R.id.text_main);
            TextView tabSubText = (TextView) spinnerView.findViewById(R.id.text_sub);
            ImageView tabIcon = (ImageView) spinnerView.findViewById(R.id.left_pic);

            tabText.setText(Constants.tabs[position]);
            tabSubText.setText(Constants.tabDesc[position]);
            tabIcon.setImageResource(Constants.tabImg[position]);

            return spinnerView;
        }
    }

}
