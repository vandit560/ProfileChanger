package com.vandit.profilechanger;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {
  boolean flag;
    private static final String TAG = "SmsBroadcastReceiver";
    SharedPreferences sp,sp1;
    static int i;
    @Override
    public void onReceive(Context context, Intent intent) {
        abortBroadcast();
        sp=context.getSharedPreferences("sms",Context.MODE_PRIVATE);
        Bundle pudsBundle=intent.getExtras();
        sp1=context.getSharedPreferences("smsflag",Context.MODE_PRIVATE);
        if(pudsBundle!=null){
        Object[] pdus= (Object[]) pudsBundle.get("pdus");
            if(pdus!=null){
            for(i=0;i<pdus.length;i++) {
    //            flag = false;
                Log.i("Destroy","Sms :"+sp1.getBoolean("flag",false));
                //flag=sp1.getBoolean("flag",false);
                SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("no", message.getOriginatingAddress());
                editor.putString("message", message.getMessageBody());
                editor.commit();
                //  smsIntent.putExtra("messageNo", message.getOriginatingAddress());
                //smsIntent.putExtra("message", message.getMessageBody());
               // context.startService(new Intent(context, MyServices.class)); //smsIntent);
             //   Toast.makeText(context, "sms Re", Toast.LENGTH_SHORT).show();
             //  Toast.makeText(context, "Sms Receiver From :" + message.getDisplayOriginatingAddress() + "\n" + message.getMessageBody(), Toast.LENGTH_SHORT).show();
               MainActivity mainActivity=new MainActivity();
                MainActivity.fla=sp1.getBoolean("flag",false);
               if(message.getMessageBody().equals("silent")|| message.getMessageBody().equals("Silent")||message.getMessageBody().equals("SILENT"))
                {

                    AudioManager audioManager;
                    if(MainActivity.fla) {
                        MainActivity.s2.setChecked(true);
                    }else {
                        if (Build.VERSION.SDK_INT < 23) {

                            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        } else {


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                int previous_notification_interrupt_setting = mainActivity.notificationManager.getCurrentInterruptionFilter();
                                mainActivity.notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                            }
                        }
                    }
                }else if(message.getMessageBody().equals("vibrate")|| message.getMessageBody().equals("Vibrate")||message.getMessageBody().equals("VIBRATE"))
                {
                    if(MainActivity.fla){
                    MainActivity.s3.setChecked(true);}
                    else {
                        AudioManager audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                    }
                } else if(message.getMessageBody().equals("normal")|| message.getMessageBody().equals("Normal")||message.getMessageBody().equals("NORMAL"))
                {
                   if(MainActivity.fla) {
                       MainActivity.s1.setChecked(true);
                   }else {
                       AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                       audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                   }
                }
            }
        }

        }
    }

    void audioch(Context context,int n){
        Toast.makeText(context, "n :"+n, Toast.LENGTH_SHORT).show();
        if(n==2 &&MainActivity.s1.isChecked()==false) {
           MainActivity.s1.setChecked(true);

        }
        else if(n==0 && MainActivity.s2.isChecked()==false) {
           MainActivity.s2.setChecked(true);

        }
        else if(n==1 &&MainActivity.s3.isChecked()==false) {
           MainActivity.s3.setChecked(true);

        }
    }
}
