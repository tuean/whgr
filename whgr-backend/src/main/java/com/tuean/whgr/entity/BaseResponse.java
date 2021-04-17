package com.tuean.whgr.entity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private int code;

    private String message;

    private T data;

    public static BaseResponse ok() {
        return BaseResponse.builder().code(0).message("ok").build();
    }

    public static BaseResponse ok(String info) {
        return BaseResponse.builder().code(0).message(info).build();
    }

    public BaseResponse<T> okWithBody(T data) {
        BaseResponse<T> res = ok();
        res.setData(data);
        return res;
    }

    public static BaseResponse okWithList(List list, boolean finished, Integer total) {
        BaseResponse<ListResponse> response = new BaseResponse();
        response.setCode(0);
        response.setMessage("ok");

        response.setData(ListResponse.builder()
                .finished(finished)
                .total(total)
                .list(list)
                .build());

        return response;
    }

    public static BaseResponse okWithEmptyList() {
        BaseResponse<ListResponse> response = new BaseResponse();
        response.setCode(0);
        response.setMessage("ok");

        response.setData(ListResponse.builder()
                .finished(true)
                .total(0)
                .list(new ArrayList<>(0))
                .build());

        return response;
    }

    public static BaseResponse error() {
        return BaseResponse.builder().code(1).message("error").build();
    }

    public static BaseResponse error(String error) {
        return BaseResponse.builder().code(1).message(error).build();
    }

    public static BaseResponse error(Integer code) {
        return BaseResponse.builder().code(code).build();
    }

    public static BaseResponse error(Integer code, String error) {
        return BaseResponse.builder().code(code).message(error).build();
    }

}
