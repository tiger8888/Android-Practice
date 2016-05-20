package com.chensuworks.service.plainservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

public class SimplePlainService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        doServiceStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        doServiceStart(intent, startId);

        return Service.START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void doServiceStart(Intent intent, int startId) {
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
