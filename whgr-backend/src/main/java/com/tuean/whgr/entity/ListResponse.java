package com.tuean.whgr.entity;

import lombok.*;

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

}
