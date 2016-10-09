package com.codekul.jsonparsing;

/**
 * Created by aniruddha on 9/10/16.
 */

public class MyPojo {

    private String one;
    private String key;

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "MyPojo{" +
                "one='" + one + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
