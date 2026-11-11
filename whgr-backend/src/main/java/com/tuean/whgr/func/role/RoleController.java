package com.tuean.whgr.func.role;

import com.tuean.whgr.annotation.RequireRole;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.AdminRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    // 角色下拉要用（账号页普通用户也能看），只读放开，增删改仍限管理员
    @RequestMapping(value = "/role/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp list() {
        return roleService.list();
    }

    @RequireRole(1)
    @RequestMapping(value = "/role/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp save(@RequestBody AdminRole role) {
        return roleService.save(role);
    }

    @RequireRole(1)
    @RequestMapping(value = "/role/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp delete(@RequestParam("id") Long id) {
        return roleService.delete(id);
    }

}
