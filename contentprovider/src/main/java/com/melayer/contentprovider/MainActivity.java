package com.melayer.contentprovider;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileProvider();
    }

    private void dbProvider(){
        ArrayList<String> contacts = new ArrayList<>();

        ContentResolver resolver = getContentResolver();
        /*Cursor cursor =
                resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);*/

        Cursor cursor =
                resolver.query(Uri.parse("content://com.codekul.proider.authority"),null,null,null,null);
        while(cursor.moveToNext()){

            //String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            String name = cursor.getString(cursor.getColumnIndex("userName"));
            String num = cursor.getString(cursor.getColumnIndex("password"));

            contacts.add(name+"\n"+num);
        }

        ((ListView)findViewById(R.id.listContacts)).setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contacts));
    }
    private void fileProvider() {

        File fileRoot = new File(getFilesDir(),"my");
        File file = new File(fileRoot,"my.txt");
        Uri uri = FileProvider.getUriForFile(this,"com.codekul.external.authority",file);
        Log.i("@codekul","File Uri - "+uri.toString());

       Intent intent = ShareCompat.IntentBuilder.from(this)
                .setStream(uri) // uri from FileProvider
                .setType("text/html")
                .getIntent()
                //.setAction(Intent.ACTION_VIEW)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivity(intent);
    }
}
