package com.tuean.whgr.dao;

import com.tuean.whgr.entity.BaseListRequest;
import com.tuean.whgr.entity.db.AdminAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminAccountMapper {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(AdminAccount record);

    int insertSelective(AdminAccount record);

    AdminAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminAccount record);

    int updateByPrimaryKey(AdminAccount record);

    AdminAccount selectByUserName(@Param("userName") String userName);

    /** selfId 非空表示数据范围=仅本人数据，只查这条记录 */
    int countBySearch(@Param("searchKey") String searchKey, @Param("selfId") Long selfId);

    List<AdminAccount> selectBySearch(@Param("request") BaseListRequest request, @Param("selfId") Long selfId);

    /** 有多少账号用着这个权限等级（删角色/改等级前校验） */
    int countByRole(@Param("level") Integer level);
}