package com.tuean.whgr.util;

import java.util.Random;
import java.util.UUID;

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

    private static final String WRAPPER_BASE = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static String getRandomString(int count) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(WRAPPER_BASE.charAt(random.nextInt(WRAPPER_BASE.length())));
        }
        return sb.toString();
    }

    public static String makeReqId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
