package com.tuean.whgr.func.admin;

import com.alibaba.fastjson.JSONObject;
import com.tuean.whgr.annotation.NoAccess;
import com.tuean.whgr.entity.BaseResponse;
import com.tuean.whgr.func.admin.param.MockListReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class TestController {

    @NoAccess
    @RequestMapping(value = "test/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse mockList(@RequestBody MockListReq req) {
        List<JSONObject> result = new ArrayList<>();
        for (int x = 0; x < req.getEndRow() - req.getStartRow(); x++) {
            JSONObject o = new JSONObject();
            o.put("id", req.getStartRow());
            o.put("name", "name" + req.getStartRow());
            o.put("time", new Date());
            result.add(o);
        }
        return BaseResponse.okWithList(result, true, 9999);
    }
}
