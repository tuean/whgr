package com.tuean.whgr.helper;

import com.tuean.whgr.util.Util;
import org.slf4j.MDC;

import static com.tuean.whgr.config.Const.REQ_ID;


public class ReqHelper {

    private static ThreadLocal<String> reqMap = new ThreadLocal<>();

    public static void init() {
        String reqId = reqMap.get();
        if (reqId == null) {
            reqId = Util.makeReqId();
            set(reqId);
        }
    }

    public static void set(String reqId) {
        MDC.put(REQ_ID, reqId);
        reqMap.set(reqId);
    }

    public static String get() {
        init();
        return reqMap.get();
    }

}
