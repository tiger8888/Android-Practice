package com.chensuworks.nativeeditor;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

public class PaginatedEditTextActivity extends AppCompatActivity {

    private TextView placeholder;
    private EditText editor;
    private String hardcodedContent = StringGenerator.generateMultipleLines(10, 3);

    private int pageSize;

    private View navigateToNextButton;
    private View navigateToPrevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paginated_layout);

        navigateToNextButton = findViewById(R.id.navigate_to_next_page);
        navigateToPrevButton = findViewById(R.id.navigate_to_prev_page);

        editor = (EditText) findViewById(R.id.paginated_edit_text);
        editor.setText(hardcodedContent);
        editor.setHorizontallyScrolling(true);

        editor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //editor.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int[] locationInWindow = new int[2];
                editor.getLocationInWindow(locationInWindow);
                Log.d("POSITION", "editor.locationInWindowX = " + locationInWindow[0] + ", editor.locationInWindowY = " + locationInWindow[1]);

                int[] locationOnScreen = new int[2];
                editor.getLocationOnScreen(locationOnScreen);
                Log.d("POSITION", "editor.locationOnScreenX = " + locationOnScreen[0] + ", editor.locationOnScreenY = " + locationOnScreen[1]);

                Log.d("SIZE", "editor.getWidth() = " + editor.getWidth() + ", editor.getHeight() = " + editor.getHeight());
                Log.d("SIZE", "editor.getTop() = " + editor.getTop() + ", editor.getBottom() = " + editor.getBottom());
                Log.d("SIZE", "editor.getLeft() = " + editor.getLeft() + ", editor.getRight() = " + editor.getRight());
                Log.d("SIZE", "editor.getLineHeight() = " + editor.getLineHeight());

                Log.d("SIZE", "POSSIBLE LINE COUNT = " + editor.getHeight() / editor.getLineHeight());
                pageSize = editor.getHeight() / editor.getLineHeight();
            }
        });

        placeholder = (TextView) findViewById(R.id.placeholder);
        placeholder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int[] locationInWindow = new int[2];
                placeholder.getLocationInWindow(locationInWindow);
                Log.d("POSITION", "placeholder.locationInWindowX = " + locationInWindow[0] + ", placeholder.locationInWindowY = " + locationInWindow[1]);

                int[] locationOnScreen = new int[2];
                placeholder.getLocationOnScreen(locationOnScreen);
                Log.d("POSITION", "placeholder.locationOnScreenX = " + locationOnScreen[0] + ", placeholder.locationOnScreenY = " + locationOnScreen[1]);

                Log.d("SIZE", "placeholder.getWidth() = " + placeholder.getWidth() + ", placeholder.getHeight() = " + placeholder.getHeight());
                Log.d("SIZE", "placeholder.getTop() = " + placeholder.getTop() + ", placeholder.getBottom() = " + placeholder.getBottom());
                Log.d("SIZE", "placeholder.getLeft() = " + placeholder.getLeft() + ", placeholder.getRight() = " + placeholder.getRight());
                Log.d("SIZE", "placeholder.getLineHeight() = " + placeholder.getLineHeight());

            }
        });

        navigateToNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "NEXT", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        navigateToPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "PREV", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });
    }


}
