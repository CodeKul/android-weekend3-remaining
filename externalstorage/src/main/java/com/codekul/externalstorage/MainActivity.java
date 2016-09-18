package com.codekul.externalstorage;

import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnWrite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePublicFile();
            }
        });

        findViewById(R.id.btnRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readPublicFile();
            }
        });
    }

    private Boolean isExternalAvailable(){
        return Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
    }

    private void saveFile(){

        if(isExternalAvailable()) {
            try {

                File file =
                        new File(getExternalFilesDir(Environment.DIRECTORY_DCIM), "my.txt");
                Log.i("@codekul","Path - "+file.getAbsolutePath());

                FileOutputStream fos = new FileOutputStream(file);
                fos.write("Android is good".getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void retriveFile(){

        if(isExternalAvailable()){

            try {
                File file =
                        new File(getExternalFilesDir(Environment.DIRECTORY_DCIM), "my.txt");

                FileInputStream fis = new FileInputStream(file);

                StringBuilder builder = new StringBuilder();

                while(true){
                    int ch = fis.read();
                    if(ch == -1) break;
                    else builder.append((char)ch);
                }
                Log.i("@codekul", builder.toString());
                fis.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void savePublicFile(){
        if(isExternalAvailable()){
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"our.txt");

            Log.i("@codekul",file.getAbsolutePath());

            try {

                FileOutputStream fos = new FileOutputStream(file);
                fos.write("Angain android is good".getBytes());
                fos.close();

                MediaScannerConnection.scanFile(this, new String[] {file.toString()}, null, null);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void readPublicFile(){
        if(isExternalAvailable()){

            File file =
                    new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"my.txt");

            try {
                FileInputStream fis = new FileInputStream(file);

                StringBuilder builder = new StringBuilder();

                while(true){
                    int ch = fis.read();
                    if(ch == -1) break;
                    else builder.append((char)ch);
                }
                Log.i("@codekul", builder.toString());
                fis.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
