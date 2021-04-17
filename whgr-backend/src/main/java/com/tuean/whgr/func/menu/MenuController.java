package com.tuean.whgr.func.menu;

import com.tuean.whgr.entity.MineResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    private static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp menus() {
        return MineResp.success();
    }

}
