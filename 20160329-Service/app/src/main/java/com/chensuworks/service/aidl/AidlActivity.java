package com.chensuworks.service.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chensuworks.service.R;

public class AidlActivity extends Activity implements ServiceConnection {

    private TextView textView;
    private EditText editText;
    private Button buttonStart;
    private IProcessText processTextService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        textView = (TextView) findViewById(R.id.text_view);
        editText = (EditText) findViewById(R.id.edit_text);
        buttonStart = (Button) findViewById(R.id.button_start);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String processedText = processTextService.processText(editText.getText().toString());
                    textView.setText(processedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Intent intent = new Intent("service.PROCESSTEXT");
        Intent intent = new Intent(this, ProcessTextService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        processTextService = IProcessText.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        processTextService = null;
    }
}
