package com.tuean.whgr;

import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String a = "1,2,4";
        List<String> r = Arrays.asList(a.split(","));
        System.out.println(r);
    }
}
