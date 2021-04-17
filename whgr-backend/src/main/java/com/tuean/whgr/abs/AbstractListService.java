package com.tuean.whgr.abs;

import com.tuean.whgr.entity.BaseListRequest;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.service.IListMapper;

public abstract class AbstractListService<T extends BaseListRequest> {

    private IListMapper listMapper;


    public MineResp list(T t) {
        int count = listMapper.countByRequest(t);
        if (count < 1) return MineResp.success();

    }



    public IListMapper getListMapper() {
        return listMapper;
    }

    public void setListMapper(IListMapper listMapper) {
        this.listMapper = listMapper;
    }

}
