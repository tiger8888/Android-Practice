package com.chensuworks.service.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.format.DateFormat;

public class ProcessTextService extends Service {

    private IProcessText.Stub mBinder = new IProcessText.Stub() {
        @Override
        public String processText(String text) throws RemoteException {
            return text + " " + DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
