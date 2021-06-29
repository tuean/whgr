package com.tuean.whgr.func.menu;

import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.Menus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    private static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/menu/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp menus(@RequestBody MenuListRequest request) {
        return menuService.menuList(request);
    }

    @RequestMapping(value = "/menu/info", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp menusInsert(@RequestBody Menus menus) {
        return menuService.insert(menus);
    }

    @RequestMapping(value = "/menu/info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp menusUpdate(@RequestBody Menus menus) {
        return menuService.update(menus);
    }

}
