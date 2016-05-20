package com.chensuworks.navigationtabs;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.chensuworks.navigationtabs.fragments.CommandFragment;
import com.chensuworks.navigationtabs.fragments.ListContentFragment;

/**
 * http://developer.android.com/guide/topics/ui/actionbar.html#Tabs
 *
 * 0. Activity should use theme which provides action bar.
 *      e.g. android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
 * 1. Implement ActionBar.TabListener interface.
 * 2. Instantiate ActionBar.Tab and set ActionBar.TabListener by calling setTabListener().
 * 3. Set tab's title and icon with setText() and setIcon().
 * 4. Add each tab to the action bar by calling addTab().
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                if (tab.getText() == "Command") {
                    ft.replace(R.id.fragment_container, new CommandFragment(), "Command");
                } else {
                    ft.replace(R.id.fragment_container, new ListContentFragment(), "Others");
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }
        };

        ActionBar.Tab tab;

        tab = actionBar.newTab().setText("Command").setTabListener(tabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Figure").setTabListener(tabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Editor").setTabListener(tabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("History").setTabListener(tabListener);
        actionBar.addTab(tab);

    }


}
