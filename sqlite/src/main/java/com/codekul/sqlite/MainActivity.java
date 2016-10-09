package com.codekul.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DbHelper helper = new DbHelper(this,Db.DB_NAME,null,Db.DB_VERSION);

        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(helper,getUserName(),getPassword());
            }
        });

        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(getUserName(),getPassword());
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(getUserName());
            }
        });

        findViewById(R.id.btnQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query(helper,getUserName());
            }
        });
    }

    private void insert(DbHelper helper, String userName, String password){

        // /data/data/package/databases/myDb
        SQLiteDatabase sqDb = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Db.My.COL_USER_NAME,userName);
        values.put(Db.My.COL_PASSWORD,password);

        sqDb.insert(Db.My.TAB_MY,null,values);
        sqDb.close();
    }

    private void query(DbHelper helper) {

        String table = Db.My.TAB_MY;
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        SQLiteDatabase sqDb =helper.getReadableDatabase();
        Cursor cursor = sqDb.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
        while (cursor.moveToNext()){
            String uNm = cursor.getString(cursor.getColumnIndex(Db.My.COL_USER_NAME));
            String pass = cursor.getString(cursor.getColumnIndex(Db.My.COL_PASSWORD));
            Log.i("@codekul","User Name - "+uNm +" Password - "+pass);
        }
        sqDb.close();
    }

    private void query(DbHelper helper, String userName){

        String table = Db.My.TAB_MY;
        String[] columns = {Db.My.COL_PASSWORD};
        String selection = Db.My.COL_USER_NAME +" = ?";
        String[] selectionArgs = {userName};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        SQLiteDatabase sqDb =helper.getReadableDatabase();
        Cursor cursor = sqDb.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
        while (cursor.moveToNext()){
            String pass = cursor.getString(cursor.getColumnIndex(Db.My.COL_PASSWORD));
            Log.i("@codekul"," Password - "+pass);
        }
        sqDb.close();
    }

    private void delete(String userName) {
    }

    private void update(String userName, String password) {
    }

    private void checkUser(String userName, String password){

    }

    private String getUserName(){
        return ((EditText)findViewById(R.id.edtUserName)).getText().toString();
    }

    private String getPassword(){
        return ((EditText)findViewById(R.id.edtPassword)).getText().toString();
    }


}
