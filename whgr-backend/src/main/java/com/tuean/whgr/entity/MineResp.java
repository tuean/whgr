package com.tuean.whgr.entity;

public class MineResp<T> {

    private Integer code;

    private String message;

    private T data;

    private static int s_code = 0;

    private static int e_code = -1;


    public MineResp(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static MineResp success() {
        return new MineResp(s_code, null, null);
    }

    public static MineResp success(String message) {
        return new MineResp(s_code, message, null);
    }

    public static MineResp success(String message, Object data) {
        return new MineResp(s_code, message, data);
    }

    public static MineResp success(Object data) {
        return new MineResp(s_code, null, data);
    }

    public static MineResp error(String message) {
        return new MineResp(e_code, message, null);
    }

    public static MineResp error() {
        return new MineResp(e_code, null, null);
    }

    public static MineResp error(Integer code, String message) {
        return new MineResp(code, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
