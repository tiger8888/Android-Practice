package com.chensuworks.service.plainservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chensuworks.service.R;

public class PlainServiceActivity extends Activity {

    private TextView textView;
    private EditText editText;
    private Button buttonStart;
    private Button buttonStop;
    private ResponseReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_service);

        textView = (TextView) findViewById(R.id.text_view);
        editText = (EditText) findViewById(R.id.edit_text);
        buttonStart = (Button) findViewById(R.id.button_start);
        buttonStop = (Button) findViewById(R.id.button_stop);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The following won't work on L5, intent must be explicit for starting a service
                /*Intent intent = new Intent("com.chensuworks.plainservice.SERVICE");
                intent.putExtra("in-msg", editText.getText().toString());
                startService(intent);*/

                /**
                 * http://developer.android.com/reference/android/app/Service.html
                 * A Service is not a separate process. The Service object itself does not imply it is running in its own process; unless otherwise specified, it runs in the same process as the application it is part of.
                 * A Service is not a thread. It is not a means itself to do work off of the main thread (to avoid Application Not Responding errors).
                 * The best way in this case is to start a new thread and then call a service from there.
                 */
                Intent intent = new Intent(v.getContext(), SimplePlainService.class);
                intent.putExtra("in-msg", editText.getText().toString());
                startService(intent);
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.chensuworks.plainservice.SERVICE");
                stopService(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter("com.chensuworks.intent.action.MESSAGE_PROCESSED");
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    private class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String processedText = intent.getStringExtra("out-msg");
            textView.setText(processedText);
        }
    }
}
