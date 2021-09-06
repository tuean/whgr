package com.tuean.whgr.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Random;

public class Util {

    public static boolean equals(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        return s1.equals(s2);
    }

    public static String eslipse(String s) {
        if (s == null) return "";
        if (s.length() < 20) return s;
        return s.substring(0, 20);
    }

    public static final String wrapperBase = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static String getRandomString(int count) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < count; ++i) {
            int number = random.nextInt(wrapperBase.length());
            sb.append(wrapperBase.charAt(number));
        }

        return sb.toString();
    }

    public static JSONArray string2array(String source) {
        return JSONObject.parseArray(source);
    }


}
