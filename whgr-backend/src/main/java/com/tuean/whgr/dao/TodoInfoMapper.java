package com.tuean.whgr.dao;

import com.tuean.whgr.entity.db.TodoInfo;
import com.tuean.whgr.func.todo.TodoListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TodoInfoMapper {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(TodoInfo record);

    int insertSelective(TodoInfo record);

    TodoInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TodoInfo record);

    int updateByPrimaryKeyWithBLOBs(TodoInfo record);

    int updateByPrimaryKey(TodoInfo record);

    List<TodoInfo> selectByRequest(TodoListRequest request);
}