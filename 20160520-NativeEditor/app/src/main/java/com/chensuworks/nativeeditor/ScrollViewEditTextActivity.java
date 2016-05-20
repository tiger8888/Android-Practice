package com.chensuworks.nativeeditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

public class ScrollViewEditTextActivity extends AppCompatActivity {

    private EditText editor;
    private ScrollView scrollView;

    private String hardcodedContent = StringGenerator.generateMultipleLines(1000, 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollview_edit_text);

        editor = (EditText) findViewById(R.id.plain_edit_text);
        editor.setText(hardcodedContent);
        //editor.setHorizontallyScrolling(true);
        //editor.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        scrollView = (ScrollView) findViewById(R.id.scrollview);
        scrollView.setSmoothScrollingEnabled(true);
    }

}
