package com.chensuworks.button;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.chensuworks.mylittlelibrary.MyView;

/**
 * Created by chensu on 7/21/16.
 */
public class CustomViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = new MyView(this);
        setContentView(view);
    }
}
