package com.tuean.whgr.dao;

import com.tuean.whgr.entity.db.AdminAccount;
import org.apache.ibatis.annotations.Param;

public interface AdminAccountMapper {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(AdminAccount record);

    int insertSelective(AdminAccount record);

    AdminAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminAccount record);

    int updateByPrimaryKey(AdminAccount record);

    AdminAccount selectByUserName(@Param("userName") String userName);
}