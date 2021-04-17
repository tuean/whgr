package com.tuean.whgr.func.admin;

import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.func.login.LoginResponse;
import com.tuean.whgr.helper.AdminAccountHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = "/adminUser/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp<LoginResponse> adminLogin() {
        return MineResp.success(AdminAccountHelper.get());
    }


}
