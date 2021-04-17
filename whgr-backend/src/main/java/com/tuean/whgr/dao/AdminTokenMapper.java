package com.tuean.whgr.dao;

import com.tuean.whgr.entity.db.AdminToken;
import org.apache.ibatis.annotations.Param;

public interface AdminTokenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminToken record);

    int insertSelective(AdminToken record);

    AdminToken selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminToken record);

    int updateByPrimaryKey(AdminToken record);

    AdminToken selectByToken(@Param("token") String token);

    void removeByToken(@Param("token") String token);
}