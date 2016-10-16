package com.codekul.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_ENABLE_BT = 1023;
    private BluetoothAdapter adapter;

    private BroadcastReceiver receiverDiscover = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.i("@codekul","Remote Name - "+remoteDevice.getName());
            Log.i("@codekul","Remote Add - "+remoteDevice.getAddress());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = BluetoothAdapter.getDefaultAdapter();

        Log.i("@codekul",""+UUID.randomUUID().toString());
        Log.i("@codekul","Name - "+adapter.getName());
        Log.i("@codekul","Address - "+adapter.getAddress());
        if(adapter == null){
            Log.i("@codekul","Bluetooth not available");
            return;
        }

        findViewById(R.id.btnEnable).setOnClickListener(this::enableBt);
        findViewById(R.id.btnPaired).setOnClickListener(this::pairedDevices);
        findViewById(R.id.btnDiscover).setOnClickListener(this::discover);
        findViewById(R.id.btnVisible).setOnClickListener(this::visibility);
        findViewById(R.id.btnServer).setOnClickListener(this::server);
        findViewById(R.id.btnClient).setOnClickListener(this::client);
    }

    private void client(View view) {

        new Thread( ()->{
            BluetoothDevice remoteDevice = adapter.getRemoteDevice("84:10:0D:A1:08:ED");
            try {
                BluetoothSocket socket = remoteDevice.createRfcommSocketToServiceRecord(UUID.fromString("00000000-0000-1000-8000-00805F9B34FB"));
                socket.connect(); // ask for pairing
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String data = dis.readUTF();
                Log.i("@codekul",data);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void server(View view) {

        new Thread(() -> {
            try {
                BluetoothServerSocket bss =
                        adapter.listenUsingRfcommWithServiceRecord("chatservice", UUID.fromString("00000000-0000-1000-8000-00805F9B34FB"));
                BluetoothSocket soc = bss.accept(); // accept connection from client
                DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
                dos.writeUTF("Coonected to bluetooth server");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void visibility(View view) {
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiverDiscover, new IntentFilter(BluetoothDevice.ACTION_FOUND));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiverDiscover);
        super.onStop();
    }

    private void discover(View view) {
        adapter.startDiscovery();
    }

    private void enableBt(View view){
        if(!adapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQ_ENABLE_BT);
        }
    }

    private void pairedDevices(View view){

        Set<BluetoothDevice> bondedDevices = adapter.getBondedDevices();
        for (BluetoothDevice bondedDevice : bondedDevices) {
            Log.i("@codekul","Address - "+bondedDevice.getAddress());
            Log.i("@codekul","Name - "+bondedDevice.getName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_ENABLE_BT){
            if(resultCode == RESULT_OK){
                Log.i("@codekul", "Bt Enabled Sucessfully ..");
            }
        }
    }
}
