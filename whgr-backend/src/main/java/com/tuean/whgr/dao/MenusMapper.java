package com.tuean.whgr.dao;

import com.tuean.whgr.entity.db.Menus;
import com.tuean.whgr.service.IListMapper;

public interface MenusMapper extends IListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menus record);

    int insertSelective(Menus record);

    Menus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menus record);

    int updateByPrimaryKey(Menus record);
}