package com.tuean.whgr.func.menu;

import com.tuean.whgr.annotation.RequireRole;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.Menus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 菜单管理页：全量菜单
     */
    @RequireRole(1)
    @RequestMapping(value = "/menu/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp all() {
        return menuService.listAllForManage();
    }

    @RequireRole(1)
    @RequestMapping(value = "/menu/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp save(@RequestBody Menus menu) {
        return menuService.save(menu);
    }

    @RequireRole(1)
    @RequestMapping(value = "/menu/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp delete(@RequestParam("id") Long id) {
        return menuService.delete(id);
    }

}
