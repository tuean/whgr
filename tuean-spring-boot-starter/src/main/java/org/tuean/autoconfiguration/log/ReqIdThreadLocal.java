package org.tuean.autoconfiguration.log;

public class ReqIdThreadLocal {

    private static ThreadLocal<String> pool = new ThreadLocal<String>();

    public static void set(String reqId) {
        pool.set(reqId);
    }

    public static String get() {
        return pool.get();
    }

    public static void remove() {
        pool.remove();
    }

}
