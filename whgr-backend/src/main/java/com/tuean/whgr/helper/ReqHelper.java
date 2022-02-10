package com.tuean.whgr.helper;

import com.tuean.whgr.util.Util;

import static com.tuean.whgr.filter.ReqFilter.REQ_ID;

public class ReqHelper {

    private static ThreadLocal<String> reqMap = new ThreadLocal<>();

    public static void set(String reqId) {
        reqMap.set(reqId);
    }

    public static String get() {
        String reqId = reqMap.get();
        if (reqId != null) {
            reqId = Util.makeReqId(REQ_ID);
        }
        return reqId;
    }

}
