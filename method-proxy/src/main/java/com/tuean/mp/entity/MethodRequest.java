package com.tuean.mp.entity;

import com.google.gson.JsonObject;

public class MethodRequest {

    private String packageName;

    private String methodName;

    private JsonObject params;


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public JsonObject getParams() {
        return params;
    }

    public void setParams(JsonObject params) {
        this.params = params;
    }
}
