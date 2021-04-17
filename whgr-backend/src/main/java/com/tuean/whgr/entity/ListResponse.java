package com.tuean.whgr.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListResponse<T> {

    private Integer total;

    private boolean finished;

    private List<T> list;


    public static ListResponse empty() {
        return ListResponse.builder()
                .list(new ArrayList<>(0))
                .finished(true)
                .total(0)
                .build();

    }

}
