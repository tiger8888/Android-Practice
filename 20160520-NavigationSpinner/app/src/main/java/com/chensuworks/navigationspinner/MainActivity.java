package com.chensuworks.navigationspinner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.chensuworks.navigationspinner.fragments.CommandFragment;
import com.chensuworks.navigationspinner.fragments.ListContentFragment;

/**
 * http://developer.android.com/guide/topics/ui/actionbar.html#Dropdown
 *
 * 1. Activity must use a theme that provides ActionBar.
 *      e.g. android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
 *
 * 2. Create s SpinnerAdapter
 *      that provides a list of selectable items for the drop-down
 *      and the layout to use when drawing each item in the list.
 *
 * 3. Implement ActionBar.onNavigationListener
 *      to define behavior that occurs when user selects an item from the list.
 *      Can replace fragment by using Fragment Manager.
 *
 * 4. In Activity.onCreate() enable action bar's drop-down list
 *      by calling setNavigationMode(NAVIGATION_MODE_LIST);
 *
 * 5. Set the callback for the drop-down list
 *      by calling setListNavigationCallbacks(mSpinnerAdpater, mNavigationCallback);
 */
public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list, android.R.layout.simple_spinner_dropdown_item);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        String[] strings = getResources().getStringArray(R.array.action_list);

        Fragment newFragment;

        if (itemPosition == 0) {
            newFragment = new CommandFragment();
        } else {
            newFragment = new ListContentFragment();
        }

        //ListContentFragment newFragment = new ListContentFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, newFragment, strings[itemPosition]);
        ft.commit();

        return true;
    }

}
