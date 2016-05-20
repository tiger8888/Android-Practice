package com.chensuworks.service.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.text.format.DateFormat;


public class SimpleIntentService extends IntentService {

    public SimpleIntentService() {
        super("SimpleIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String userInput = intent.getStringExtra("in-msg");
        SystemClock.sleep(3000);
        String processedText = userInput + " " + DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.chensuworks.intent.action.MESSAGE_PROCESSED");
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra("out-msg", processedText);
        sendBroadcast(broadcastIntent);
    }


}
