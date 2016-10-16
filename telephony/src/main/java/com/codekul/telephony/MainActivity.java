package com.codekul.telephony;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_SENT = 1234;
    private static final int REQ_DELIVERED = 4561;

    private TelephonyManager manager;

    private BroadcastReceiver receiverSms = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i("@codekul","Action - "+intent.getAction());

            if (intent.getAction().equals("com.codekul.action.SENT")) {
                Log.i("@codekul", "Msg Sent");
            } else {
                Log.i("@codekul", "Msg Delivered");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        Log.i("@codekul", "Imei - " + manager.getDeviceId());
        Log.i("@codekul", "Mobile No - " + manager.getLine1Number());
        Log.i("@codekul", "Iso - " + manager.getSimCountryIso());
        Log.i("@codekul", "Nw Operator - " + manager.getNetworkOperator());
        Log.i("@codekul", "Sim Operator - " + manager.getSimOperator());

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("@codekul","Called");
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Log.i("@codekul","Sending msg");
                sendMsg();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.codekul.action.SENT");
        filter.addAction("com.codekul.action.DELIVERED");
        registerReceiver(receiverSms, filter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiverSms);
        super.onStop();
    }

    @RequiresPermission(value = Manifest.permission.SEND_SMS)
    private void sendMsg(){
        SmsManager manager = SmsManager.getDefault();

        Intent intentSent = new Intent("com.codekul.action.SENT");
        PendingIntent pendingIntentSent = PendingIntent.getBroadcast(this,REQ_SENT,intentSent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentDeliverd = new Intent("com.codekul.action.DELIVERED");
        PendingIntent pendingIntentDelivered = PendingIntent.getBroadcast(this,REQ_DELIVERED,intentDeliverd,PendingIntent.FLAG_UPDATE_CURRENT);

        //manager.sendTextMessage("destination-no",null, "Hi1, Android",pendingIntentSent,pendingIntentDelivered);
        manager.sendTextMessage("destination-no",null, "Hi2, Android",pendingIntentSent,pendingIntentDelivered);
        manager.sendTextMessage("destination-no",null, "Hi3, Android",pendingIntentSent,pendingIntentDelivered);
    }
}
