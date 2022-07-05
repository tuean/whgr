package com.tuean.whgr.func.login;

import com.tuean.whgr.dao.AdminAccountMapper;
import com.tuean.whgr.dao.AdminTokenMapper;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.entity.db.AdminToken;
import com.tuean.whgr.enums.AdminLoginTypeEnum;
import com.tuean.whgr.enums.CommonStatus;
import com.tuean.whgr.helper.AdminAccountHelper;
import com.tuean.whgr.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.tuean.whgr.config.Const.*;

@Service
public class LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private AdminAccountMapper adminAccountMapper;

    @Autowired
    private AdminTokenMapper adminTokenMapper;

    public LoginResponse login(LoginRequest request) {
        AdminAccount adminAccount = adminAccountMapper.selectByUserName(request.getUserName());
        if (adminAccount == null) throw new RuntimeException(LOGIN_ERROR);
        if (!Util.equals(request.getPasswordMd5(), adminAccount.getPwd())) throw new RuntimeException(LOGIN_ERROR);
        AdminAccountHelper.put(adminAccount);

        Long adminId = adminAccount.getId();
        String token = Util.getRandomString(TOKEN_LENGTH);
        // 添加新的token信息
        AdminToken adminToken = AdminToken.builder()
                .adminId(adminId)
                .token(token)
                .tokenType(AdminLoginTypeEnum.PC.getCode())
                .createTime(new Date())
                .modifyTime(new Date())
                .sessionKey("")
                .status(CommonStatus.OK.getCode())
                .build();
        adminTokenMapper.insert(adminToken);

        return LoginResponse.builder()
                .token(token)
                .loginUserName(adminAccount.getAccount())
                .nickName(adminAccount.getName())
                .build();
    }

    public void logout() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(TOKEN);
        adminTokenMapper.removeByToken(token);
    }

}
