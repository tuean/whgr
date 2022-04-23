package com.tuean.whgr.func.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    @Autowired
    private TodoInfoMapper todoInfoMapper;

    public List<TodoInfo> getTodoInfo(TodoListRequest request) {
        return todoInfoMapper.selectByRequest(request);
    }

    public void insert(TodoInfo info) {
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        info.setId(null);
        todoInfoMapper.insertSelective(info);
    }

    public TodoInfo update(TodoInfo info) {
        info.setUpdateTime(new Date());
        todoInfoMapper.updateByPrimaryKeySelective(info);
        return info;
    }

    public void remove(Long id) {
        todoInfoMapper.deleteByPrimaryKey(id);
    }
}
