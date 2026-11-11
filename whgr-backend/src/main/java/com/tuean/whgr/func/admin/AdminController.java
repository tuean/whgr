package com.tuean.whgr.func.admin;

import com.tuean.whgr.annotation.RequireRole;
import com.tuean.whgr.entity.BaseListRequest;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.func.login.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/adminUser/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp<LoginResponse> adminLogin() {
        return MineResp.success(adminService.getInfo());
    }

    // 登录即可：普通用户靠数据权限只能看到自己
    @RequestMapping(value = "/admin/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp list(@RequestBody BaseListRequest request) {
        return adminService.list(request);
    }

    @RequireRole(1)
    @RequestMapping(value = "/admin/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp save(@RequestBody AdminAccount account) {
        return adminService.save(account);
    }

    @RequireRole(1)
    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp delete(@RequestParam("id") Long id) {
        return adminService.delete(id);
    }

}
