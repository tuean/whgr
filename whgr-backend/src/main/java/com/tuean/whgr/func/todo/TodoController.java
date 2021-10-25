package com.tuean.whgr.func.todo;

import cn.hutool.core.lang.Assert;
import com.tuean.whgr.annotation.NoAccess;
import com.tuean.whgr.entity.BaseResponse;
import com.tuean.whgr.entity.db.TodoInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    @NoAccess
    @RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getList(@RequestBody TodoListRequest request) {
        return BaseResponse.success(todoService.getTodoInfo(request));
    }

    @NoAccess
    @RequestMapping(value = "/info", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse create(@RequestBody TodoInfo info) {
        todoService.insert(info);
        return BaseResponse.ok();
    }

    @NoAccess
    @RequestMapping(value = "/info", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody TodoInfo info) {
        todoService.update(info);
        return BaseResponse.ok();
    }

    @NoAccess
    @RequestMapping(value = "/info/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse remove(@PathVariable("id") Long id) {
        Assert.notNull(id);
        todoService.remove(id);
        return BaseResponse.ok();
    }
}
