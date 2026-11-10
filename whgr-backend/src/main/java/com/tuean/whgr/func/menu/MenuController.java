package com.tuean.whgr.func.menu;

import com.tuean.whgr.entity.MineResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 侧边菜单：前端登录后拉一次，直接按返回顺序渲染
     */
    @RequestMapping(value = "/menu/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp list() {
        return menuService.listAll();
    }

}
