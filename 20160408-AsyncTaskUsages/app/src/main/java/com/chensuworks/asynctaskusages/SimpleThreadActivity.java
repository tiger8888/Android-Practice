package com.chensuworks.asynctaskusages;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

public class SimpleThreadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count);

        final TextView tv = (TextView) findViewById(R.id.counter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;

                while (i < 100) {
                    SystemClock.sleep(250);
                    i++;

                    final int currentCount = i;
                    if (currentCount % 5 == 0) {
                        // update UI with progress every 5%
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(currentCount + "% Complete!");
                            }
                        });
                    }
                }

                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("Count Complete!");
                    }
                });

            }
        }).start();
    }
}
