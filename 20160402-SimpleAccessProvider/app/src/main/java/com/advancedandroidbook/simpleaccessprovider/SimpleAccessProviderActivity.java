package com.advancedandroidbook.simpleaccessprovider;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;

public class SimpleAccessProviderActivity extends Activity {

    private Uri mUri = Uri.parse("content://com.advancedandroidbook.simplesearchprovider.SimpleFieldnotesContentProvider/fieldnotes_provider");
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        mCursor = getContentResolver().query(mUri, null, null, null, "fieldnotes_title");

        ListView listView = (ListView) findViewById(R.id.provider);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.two_line_list_item,
                mCursor,
                new String[] { "FIELDNOTES_TITLE", "FIELDNOTES_BODY" },
                new int[] { android.R.id.text1, android.R.id.text2 },
                0
        );
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }

        super.onDestroy();
    }
}