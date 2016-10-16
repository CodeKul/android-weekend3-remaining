package com.codekul.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // just 20 years of java and it is just begining

    private SensorManager manager;
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Log.i("@codekul","Val - "+event.values[0]);

            illumination(event.values);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*findViewById(R.id.btnOkay).setOnClickListener(v -> { // lambda
        });*/

        findViewById(R.id.btnOkay)
                .setOnClickListener(this::okayClick); // method ref

       /* View.OnClickListener listener = this::okayClick;
        listener = v -> { };
        listener.onClick(null);*/

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList(manager);
    }

    private void okayClick(View v){

    }

    private void sensorList(SensorManager manager){
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            Log.i("@codekul","Name - "+sensor.getName());
            Log.i("@codekul","Vendor - "+sensor.getVendor());
            Log.i("@codekul","Type - "+sensor.getPower());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        manager.registerListener(listener,
                manager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);

        manager.registerListener(new SensorEventListener() {
                                     @Override
                                     public void onSensorChanged(SensorEvent event) {
                                        Log.i("@codekul","Proximity - "+event.values[0]);
                                     }

                                     @Override
                                     public void onAccuracyChanged(Sensor sensor, int accuracy) {

                                     }
                                 },
                manager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {

        manager.unregisterListener(listener);
        super.onStop();
    }

    private void proximity(float...values){

        if(values[0] >= 100){
            findViewById(R.id.layoutRoot)
                    .setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimaryDark));
        }else {
            findViewById(R.id.layoutRoot)
                    .setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.pureWhite));
        }
    }

    private void illumination(float ... values){
        Log.i("@codekul", "Illuminatio - "+values[0]);
    }
}
