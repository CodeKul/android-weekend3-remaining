package com.codekul.remaining;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver receviver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
                        changeBattery(R.drawable.ic_battery_charging_full_black_24dp);
                    else changeBattery(R.drawable.ic_battery_full_black_24dp);
                }
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.codekul.custom");
                //sendBroadcast(intent);
                sendOrderedBroadcast(intent,"com.codekul.receiver.permission");

                IntentFilter ifilter =
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, ifilter);

                // Are we charging / charged?
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                Log.i("@codekul",String.valueOf(status));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receviver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receviver);
    }

    private void changeBattery(int image){
        ((ImageView)findViewById(R.id.imageView)).setImageResource(image);
    }
}
