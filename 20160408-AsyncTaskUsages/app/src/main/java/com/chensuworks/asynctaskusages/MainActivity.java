package com.chensuworks.asynctaskusages;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // there should be "@android:id/list" and "@android:id/empty"

        String[] list = new String[] {
                "Simple Thread",
                "AsyncTask",
                "Multiple AsyncTask",
                "Restartable AsyncTask"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row_layout, R.id.row_text, list);

        setListAdapter(adapter); // in the view, there must be a ListView with id "@android:id/list"
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        String selectedItem = (String) getListView().getItemAtPosition(position);
        Toast.makeText(getApplicationContext(), "Text: " + selectedItem + ", ID: " + id, Toast.LENGTH_SHORT).show();

        switch ((int) id) {
            case 0:
                startActivityForResult(new Intent(getApplicationContext(), SimpleThreadActivity.class), 10);
                break;
            case 1:
                startActivityForResult(new Intent(getApplicationContext(), SimpleAsyncTaskActivity.class), 11);
                break;
            case 2:
                startActivityForResult(new Intent(getApplicationContext(), SimpleMultiCoreAsyncTaskActivity.class), 12);
                break;
            case 3:
                startActivityForResult(new Intent(getApplicationContext(), RestartableAsyncTaskActivity.class), 13);
                break;
            default:
                // do nothing
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                Toast.makeText(this, "return from SimpleThreadActivity", Toast.LENGTH_SHORT).show();
                break;
            case 11:
                Toast.makeText(this, "return from SimpleAsyncTaskActivity", Toast.LENGTH_SHORT).show();
                break;
            case 12:
                Toast.makeText(this, "return from SimpleMultiCoreAsyncTaskActivity", Toast.LENGTH_SHORT).show();
                break;
            case 13:
                Toast.makeText(this, "return from RestartableAsyncTaskActivity", Toast.LENGTH_SHORT).show();
                break;
            default:
                // do nothing
        }
    }

}
