package com.codekul.sharedprefs;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_BOOL = "boolean";
    private static final String KEY_INT = "int";
    private static final String PREFS_MY = "myPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getPreferences(MODE_PRIVATE); // activity level

                prefs = getSharedPreferences(PREFS_MY,MODE_PRIVATE); // app level

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(KEY_BOOL,true);
                editor.putInt(KEY_INT,10);

                editor.commit();
            }
        });

        findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences(PREFS_MY,MODE_PRIVATE);
                int _int = prefs.getInt(KEY_INT,-1);
                Boolean bool = prefs.getBoolean(KEY_BOOL,false);

                Log.i("@codekul","Int - "+ _int+"Bool - "+bool);
            }
        });
    }
}
