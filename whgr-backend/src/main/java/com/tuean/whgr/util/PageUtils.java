package com.tuean.whgr.util;


import com.tuean.whgr.entity.PageTair;

import static com.tuean.whgr.Const.NO_PAGE_SIGN;

public class PageUtils {

    public static PageTair transferTo(Integer pageNo, Integer pageSize) {
        if (NO_PAGE_SIGN.equals(pageSize)) return PageTair.builder().startRow(-1).endRow(-1).build();

        if (pageNo == null || pageNo < 1 || pageSize == null || pageSize < 1) {
            throw new RuntimeException("分页参数有误");
        }

        int start = (pageNo - 1) * pageSize;
        return new PageTair(start, pageSize);
    }

}
