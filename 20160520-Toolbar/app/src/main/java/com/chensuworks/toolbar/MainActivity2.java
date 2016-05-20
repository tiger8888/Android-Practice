package com.chensuworks.toolbar;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * http://www.android4devs.com/2014/12/how-to-make-material-design-app.html
 * https://blog.danielbetts.net/2015/01/02/material-design-spinner-toolbar-style-fix/
 * http://mrbool.com/how-to-customize-spinner-in-android/28286
 */
public class MainActivity2 extends AppCompatActivity {

    private Toolbar mToolbar;

    private String[] spinnerTexts = {"Command", "Figure", "Editor", "History", "Sensor"};
    private String[] spinnerSubs = {"command window", "figure list", "editor window", "history window", "sensor window"};
    private int[] images = {R.drawable.ic_visa, R.drawable.ic_search, R.drawable.ic_user, R.drawable.ic_visa, R.drawable.ic_user};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setNavigationIcon(R.drawable.ic_nav);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "navigation icon", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.toolbar_spinner);
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.navigation_list,
                android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(spinnerAdapter);

        spinner.setAdapter(new CustomArrayAdapter(this, R.layout.toolbar_spinner_item, spinnerTexts));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, which adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actoin bar item clicks here.
        // The action bar will automatically handle clicks on the Home/Up button,
        // so long as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_user:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class CustomArrayAdapter extends ArrayAdapter<String> {

        public CustomArrayAdapter(Context context, int txtViewResourceId, String[] strings) {
            super(context, txtViewResourceId, strings);
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
            View spinnerView = inflater.inflate(R.layout.toolbar_spinner_item, parent, false);

            TextView mainText = (TextView) spinnerView.findViewById(R.id.text_main);
            mainText.setText(spinnerTexts[position]);

            TextView subText = (TextView) spinnerView.findViewById(R.id.text_sub);
            subText.setText(spinnerSubs[position]);

            ImageView leftImage = (ImageView) spinnerView.findViewById(R.id.left_pic);
            leftImage.setImageResource(images[position]);

            return spinnerView;
        }
    }












}
