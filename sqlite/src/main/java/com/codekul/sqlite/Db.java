package com.codekul.sqlite;

import android.util.Log;

/**
 * Created by aniruddha on 18/9/16.
 */
public class Db {

    public static final String DB_NAME = "mhyDb";
    public static final int DB_VERSION = 1;


    public static final class My {

        public static final String TAB_MY = "myTab";

        public static final String COL_USER_NAME = "userName";
        public static final String COL_PASSWORD = "password";

        public static final String query(){
            StringBuilder builder = new StringBuilder();
            builder.append("create table ").append(TAB_MY)
                    .append(" ( ")
                    .append(COL_USER_NAME).append(" text")
                    .append(",")
                    .append(COL_PASSWORD).append(" text")
                    .append(")");
            Log.i("@codekul","MyTable - "+builder.toString());
            return builder.toString();
        }
    }
}
