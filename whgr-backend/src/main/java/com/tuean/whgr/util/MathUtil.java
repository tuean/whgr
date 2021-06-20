package com.tuean.whgr.util;

import java.util.Random;

public class MathUtil {

    private static Random random = new Random();

    public static Integer random(Integer start, Integer end) {
//        Integer r = random.nextInt();
//        rand.nextInt(90) + 10;
        return random.nextInt(end - start) + start;
    }


}
