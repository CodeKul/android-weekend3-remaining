package com.codekul.jsonparsing;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniruddha on 9/10/16.
 */

public class MyParser {

    private static Gson gson = new Gson();

    public static List<String> allNames(String jsonString){

        List<String> names = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray nameArray = obj.getJSONArray("names");
            for(int i = 0; i < nameArray.length() ; i ++){

                JSONObject innerObj = nameArray.getJSONObject(i);
                names.add(innerObj.getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return names;
    }

    public String nthName(int n){
        throw new RuntimeException();
    }

    public static List<String> allNames(String jsonString, Boolean isUgly){

        List<String> names = new ArrayList<>();
        try {
            JSONArray nameArray = new JSONArray(jsonString);
            for(int i = 0; i < nameArray.length() ; i ++){

                JSONObject innerObj = nameArray.getJSONObject(i);
                names.add(innerObj.getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return names;
    }

    public static MyPojo parseUsingGson(String json){

        MyPojo pojo = gson.fromJson(json,MyPojo.class);

        return pojo;
    }
}
