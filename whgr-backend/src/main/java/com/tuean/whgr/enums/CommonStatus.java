package com.tuean.whgr.enums;

public enum CommonStatus {

    OK          (0),

    INVALID     (1)

    ;

    private Integer code;

    CommonStatus(Integer code) {
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }
}
