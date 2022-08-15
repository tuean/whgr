package org.tuean.autoconfiguration.util;

import java.util.Random;

public class Util {

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

}
