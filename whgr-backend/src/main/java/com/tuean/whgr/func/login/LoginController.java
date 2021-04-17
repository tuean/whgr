package com.tuean.whgr.func.login;

import com.tuean.whgr.annotation.NoAccess;
import com.tuean.whgr.entity.MineResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @NoAccess
    @RequestMapping(value = "/adminUser/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp<LoginResponse> adminLogin(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = loginService.login(request);
        return MineResp.success(loginResponse);
    }

    @NoAccess
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MineResp<LoginResponse> adminLogin() {
        loginService.logout();
        return MineResp.success();
    }

}
