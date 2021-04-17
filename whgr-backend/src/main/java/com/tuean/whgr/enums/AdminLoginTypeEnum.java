package com.tuean.whgr.enums;

public enum AdminLoginTypeEnum {


    PC          (1),

    WX          (2)

    ;

    private Integer code;

    AdminLoginTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
