package com.tuean.whgr.dao;

import com.tuean.whgr.entity.db.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminRoleMapper {

    List<AdminRole> selectAll();

    AdminRole selectByPrimaryKey(@Param("id") Long id);

    AdminRole selectByLevel(@Param("level") Integer level);

    int insertSelective(AdminRole record);

    int updateByPrimaryKeySelective(AdminRole record);

    int deleteByPrimaryKey(@Param("id") Long id);

}
