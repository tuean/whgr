package com.tuean.whgr.service;

import com.tuean.whgr.entity.BaseListRequest;

import java.util.List;

public interface IListMapper<T extends BaseListRequest> {

    int countByRequest(T t);

    List selectByRequest(T t);

}
