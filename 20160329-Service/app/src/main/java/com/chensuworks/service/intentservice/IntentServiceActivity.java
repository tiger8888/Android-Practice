package com.chensuworks.service.intentservice;

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

public class IntentServiceActivity extends Activity {

    private TextView textView;
    private EditText editText;
    private Button button;
    private ResponseReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        textView = (TextView) findViewById(R.id.text_view);
        editText = (EditText) findViewById(R.id.edit_text);
        button = (Button) findViewById(R.id.button_start);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editText.getText().toString();
                Intent intent = new Intent(v.getContext(), SimpleIntentService.class);
                intent.putExtra("in-msg", userInput);
                startService(intent);
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
    protected void onPause() {
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
