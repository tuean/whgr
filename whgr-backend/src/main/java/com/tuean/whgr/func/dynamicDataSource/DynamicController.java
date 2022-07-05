package com.tuean.whgr.func.dynamicDataSource;


import com.tuean.whgr.config.RoutableDataSource;
import com.tuean.whgr.dao.AdminAccountMapper;
import com.tuean.whgr.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DynamicController {

    @Autowired
    private AdminAccountMapper adminAccountMapper;

    @GetMapping(value = "/dynamic")
    public void dyncmic(@RequestHeader("db") String db,
                                @RequestParam("id") Long id) {
        RoutableDataSource.set(db);
        log.info(String.valueOf(adminAccountMapper.selectByPrimaryKey(id) == null));
    }


}
