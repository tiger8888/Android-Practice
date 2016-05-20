package com.chensuworks.nativeeditor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class PlainEditTextActivity extends AppCompatActivity {

    private EditText editor;
    private String hardcodedContent = StringGenerator.generateMultipleLines(1000, 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plain_edit_text);

        editor = (EditText) findViewById(R.id.plain_edit_text);
        editor.setText(hardcodedContent);
        editor.setHorizontallyScrolling(true);
        editor.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

}
