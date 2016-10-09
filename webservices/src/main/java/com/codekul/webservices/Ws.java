package com.codekul.webservices;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by aniruddha on 9/10/16.
 */

public class Ws {

    private static RequestQueue queue;
    public static RequestQueue q(Context context){
        return queue == null ? Volley.newRequestQueue(context) : queue;
    }
}
