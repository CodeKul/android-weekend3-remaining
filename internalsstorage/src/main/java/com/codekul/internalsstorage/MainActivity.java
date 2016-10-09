package com.codekul.internalsstorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_MY = "myFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnWrite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FileOutputStream fos = openFileOutput(FILE_MY, MODE_APPEND);
                    fos.write("Android File Internal Storage".getBytes());
                    fos.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btnRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // /data/data/your-package/files
                try{
                    FileInputStream fis = openFileInput(FILE_MY);
                    StringBuilder builder = new StringBuilder();
                    while(true){

                        int ch = fis.read();
                        if(ch == -1) break;
                        else builder.append((char)ch);
                    }

                    Log.i("@codekul",builder.toString());
                    fis.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btnUseful).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File fileRoot = new File(getFilesDir(),"my");
                File file = new File(fileRoot,"my.txt");
                try {
                    fileRoot.mkdir();
                    file.createNewFile();

                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write("file provide data".getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("@codekul","Path is "+file.getAbsolutePath());

                /*File dir = new File(getFilesDir(),"my");
                dir.mkdir();

                for (String s : fileList()) {
                    Log.i("@codekul","Data is "+s);
                }

                Log.i("@codekul",getDir("myDir",MODE_PRIVATE).getAbsolutePath());*/
            }
        });
    }
}
