package com.chensuworks.accesscalendarprovider;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            ListView listView = (ListView) findViewById(R.id.list_view);

            /*
            cursor = getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.two_line_list_item,
                    cursor,
                    new String[] {CalendarContract.Calendars.ACCOUNT_NAME, CalendarContract.Calendars.ACCOUNT_TYPE},
                    new int[] {android.R.id.text1, android.R.id.text2},
                    0
            );

            */

            cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, null, null, null, null);

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.two_line_list_item,
                    cursor,
                    new String[] {CalendarContract.Events.TITLE, CalendarContract.Events.ACCOUNT_NAME},
                    new int[] {android.R.id.text1, android.R.id.text2},
                    0
            );

            listView.setAdapter(adapter);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        cursor.close();

        super.onPause();
    }

}
