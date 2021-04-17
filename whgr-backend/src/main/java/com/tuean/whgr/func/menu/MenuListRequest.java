package com.tuean.whgr.func.menu;

import com.tuean.whgr.entity.BaseListRequest;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuListRequest extends BaseListRequest {

    private String name;

    private Integer type;

}
