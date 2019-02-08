package com.vandit.profilechanger;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
       static Switch s1,s2,s3;
        AudioManager audioManager;
    Context context;
        ImageView img;
      public static boolean fla=true;
        int no;
      NotificationManager notificationManager;
        SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        sp=context.getSharedPreferences("smsflag",Context.MODE_PRIVATE);
        startService(new Intent(this,SmsBroadcast.class));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        101);
            }
        } else {
         }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                        101);
            }
        } else {
        }
        if(Build.VERSION.SDK_INT>23) {
           notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int previous_notification_interrupt_setting = notificationManager.getCurrentInterruptionFilter();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }
        }
        s1=findViewById(R.id.switch1);
        s2=findViewById(R.id.switch2);
        s3=findViewById(R.id.switch3);
        img=findViewById(R.id.img);
        context=this;


        audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int n=audioManager.getRingerMode();
        if(n==2) {
            img.setImageResource(R.drawable.normal);
            s1.setChecked(true);

        }
        else if(n==0) {
            img.setImageResource(R.drawable.mute);
            s2.setChecked(true);
        }
        else if(n==1) {
            img.setImageResource(R.drawable.normal);
            s3.setChecked(true);
        }

        s1.setOnCheckedChangeListener(this);
        s2.setOnCheckedChangeListener(this);
        s3.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(R.id.switch1==compoundButton.getId()) {
            if (b==true){
                img.setImageResource(R.drawable.ic_normal_24dp);
               no=1;
                audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                if(s2.isChecked()){
                    s2.setChecked(false);
                }if(s3.isChecked()){
                    s3.setChecked(false);
                }
            }
            else{
                }

        }
        else if(R.id.switch2==compoundButton.getId()) {
            if (b==true) {
                img.setImageResource(R.drawable.ic_silent_24dp);
                if(Build.VERSION.SDK_INT<23){

                    audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                else{


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                          int previous_notification_interrupt_setting = notificationManager.getCurrentInterruptionFilter();
                            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                        }
                    }
                    no=2;
                if(s1.isChecked()){
                    s1.setChecked(false);
                }if(s3.isChecked()){
                    s3.setChecked(false);
                }
            }
            else{
            if(no==2) {
                s1.setChecked(true);
            }
            }

        }
       else if(R.id.switch3==compoundButton.getId()) {
            if (b==true){
                img.setImageResource(R.drawable.ic_vibration_24dp);
                audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                no=3;
                if(s2.isChecked()){
                    s2.setChecked(false);
                }if(s1.isChecked()){
                    s1.setChecked(false);
                }
            }
            else{
                if(no==3) {
                    s1.setChecked(true);
                }
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("flag",true);
        editor.commit();
        fla=true;
        Log.i("Destroy","Back Pressed :"+sp.getBoolean("flag",false));

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("flag",true);
        editor.commit();

        fla=true;
        Log.i("Destroy","Pause :"+sp.getBoolean("flag",false));

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("flag",true);
        editor.commit();
        audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int n=audioManager.getRingerMode();
        if(n==2) {
            img.setImageResource(R.drawable.ic_normal_24dp);
            s1.setChecked(true);

        }
        else if(n==0) {
            img.setImageResource(R.drawable.ic_silent_24dp);
            s2.setChecked(true);
        }
        else if(n==1) {
            img.setImageResource(R.drawable.ic_vibration_24dp);
            s3.setChecked(true);
        }

        fla=true;
        Log.i("Destroy","Resume :"+sp.getBoolean("flag",false));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("flag",false);
        editor.commit();

        fla=false;
        Log.i("Destroy","Destroy :"+sp.getBoolean("flag",false));

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("flag",false);
        editor.commit();

        fla=false;
        Log.i("Destroy","Stop :"+sp.getBoolean("flag",false));

    }
}

