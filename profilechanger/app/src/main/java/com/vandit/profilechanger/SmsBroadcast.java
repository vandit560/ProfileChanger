package com.vandit.profilechanger;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class SmsBroadcast extends Service {
    BroadcastReceiver mbroadcastReceiver;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("OnCreate","create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mbroadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("receiver","RECE");
                Toast.makeText(context, "OnReceiver", Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter intentFilter=new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(mbroadcastReceiver,intentFilter);
     int i=0;
      while(i<10) {
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          Log.e("start", "startId"+i++);

      }
      return START_STICKY;
    }
}
