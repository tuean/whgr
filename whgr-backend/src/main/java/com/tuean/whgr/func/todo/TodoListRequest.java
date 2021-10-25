package com.tuean.whgr.func.todo;

import com.tuean.whgr.entity.BaseListRequest;

public class TodoListRequest extends BaseListRequest {

    private String title;

    private String time;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
