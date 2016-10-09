package com.codekul.webservices;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.codekul.jsonparsing.MyParser;
import com.codekul.jsonparsing.MyPojo;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // https://github.com/aniruddhha/recycler-view-web-services
        findViewById(R.id.btnParse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputStream is = getAssets().open("my.json");
                    StringBuilder builder = new StringBuilder();

                    while(true){
                        int ch = is.read();
                        if(ch == -1) break;
                        else builder.append((char)ch);
                    }

                    List<String> names = MyParser.allNames(builder.toString());
                    ((ListView)findViewById(R.id.listNames))
                            .setAdapter(new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,names));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btnFetch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJson();
            }
        });
    }

    private void toPojo(){
        Ws.q(MainActivity.this).add(new JsonObjectRequest("http://echo.jsontest.com/key/value/one/two", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("@codekul",response.toString());
                MyPojo pojo = MyParser.parseUsingGson(response.toString());
                Log.i("@codekul", "KEY - "+pojo.getKey());
                Log.i("@codekul", "One - "+pojo.getOne());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("@codekul",error.toString());
            }
        }));
    }
    private void randomNames(){

        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this,"Names","you will get 10 random names");
        Ws.q(MainActivity.this).add(new StringRequest("http://uinames.com/api/?amount=10", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("@codekul",response.toString());

                List<String> names = MyParser.allNames(response,true);
                ((ListView)findViewById(R.id.listNames))
                        .setAdapter(new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,names));

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("@codekul",error.toString());
                progressDialog.dismiss();
            }
        }));
    }

    private void postJson(){

        JSONObject obj = new JSONObject();
        try{
            obj.put("title","codekul.com");
            obj.put("body","Android training");
            obj.put("userId",10);
        }catch (Exception e){
            e.printStackTrace();
        }

        Ws.q(this).add(new JsonObjectRequest(Request.Method.POST,
                "http://jsonplaceholder.typicode.com/posts", obj,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("@codekul","Response - "+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("@codekul","Response - "+error.toString());
            }
        }));
    }
}
