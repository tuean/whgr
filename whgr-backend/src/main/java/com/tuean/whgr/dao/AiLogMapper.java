package com.tuean.whgr.dao;

import com.tuean.whgr.entity.db.AiLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AiLogMapper {

    Integer insert(AiLog log);

    AiLog selectById(@Param("id") Long id);

    Integer countByAdmin(@Param("adminId") Long adminId);

    List<AiLog> selectByAdmin(@Param("adminId") Long adminId,
                              @Param("startRow") Integer startRow,
                              @Param("endRow") Integer endRow);

}
