package com.codekul.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WifiManager manager;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> scanResults = manager.getScanResults();
            for (ScanResult scanResult : scanResults) {
                Log.i("@codekul", " Scaned SSID - " +scanResult.SSID);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (WifiManager) getSystemService(WIFI_SERVICE);

        findViewById(R.id.btnConnected)
                .setOnClickListener(this::connected);

        findViewById(R.id.btnScan)
                .setOnClickListener(this::scan);

        findViewById(R.id.btnConnect)
                .setOnClickListener(this::connect);
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(receiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
    }
    private void connected(View v){
        List<WifiConfiguration> configurations
                = manager.getConfiguredNetworks();

        for (WifiConfiguration configuration : configurations) {
            Log.i("@codekul", "BSSID - "+configuration.BSSID);
            Log.i("@codekul", "SSID - "+configuration.SSID);
        }
    }

    private void scan(View view) {
        manager.startScan();
    }

    private void connect(View view){

        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", "YOUR CODEKUL");
        wifiConfig.preSharedKey = String.format("\"%s\"", "code.com;");

        WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }
}
