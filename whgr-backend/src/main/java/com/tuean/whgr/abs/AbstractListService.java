package com.tuean.whgr.abs;

import com.tuean.whgr.entity.BaseListRequest;
import com.tuean.whgr.entity.ListResponse;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.service.IListMapper;

import java.util.List;

public abstract class AbstractListService<T extends BaseListRequest> {

    private IListMapper listMapper;


    public MineResp list(T t, IListMapper listMapper) {
        this.listMapper = listMapper;
        return list(t);
    }

    public MineResp list(T t) {
        int count = listMapper.countByRequest(t);
        if (count < 1) return MineResp.success(ListResponse.empty());
        List list = listMapper.selectByRequest(t);
        List result = transfer(list);
        boolean flag = true; // todo
        return MineResp.success(new ListResponse(count, flag, result));
    }


    public abstract List transfer(List list);

    public IListMapper getListMapper() {
        return listMapper;
    }

    public void setListMapper(IListMapper listMapper) {
        this.listMapper = listMapper;
    }

}
