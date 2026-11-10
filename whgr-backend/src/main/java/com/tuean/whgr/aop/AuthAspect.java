package com.tuean.whgr.aop;


import com.tuean.whgr.annotation.NoAccess;
import com.tuean.whgr.annotation.RequireRole;
import com.tuean.whgr.dao.AdminAccountMapper;
import com.tuean.whgr.dao.AdminTokenMapper;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.entity.db.AdminToken;
import com.tuean.whgr.exception.NeedLoginException;
import com.tuean.whgr.exception.NoPermissionException;
import com.tuean.whgr.helper.AdminAccountHelper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import static com.tuean.whgr.config.Const.TOKEN;


@Component
@Aspect
@Order(10)
public class AuthAspect {

    private static Logger logger = LoggerFactory.getLogger(AuthAspect.class);

    /**
     * 所有 func 下的接口默认要登录；@GetMapping/@PostMapping 等也要覆盖，
     * 否则用短注解写的接口会静默跳过鉴权
     */
    @Pointcut("execution(public * com.tuean.whgr.func.*.*.*(..)) && ("
            + "@annotation(org.springframework.web.bind.annotation.RequestMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PatchMapping))")
    private void pointcut(){}

    @Autowired
    private AdminTokenMapper adminTokenMapper;

    @Autowired
    private AdminAccountMapper adminAccountMapper;

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String tokenInHeader = request.getHeader(TOKEN);

        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        NoAccess noAccess = method.getAnnotation(NoAccess.class);
        if (noAccess != null) return point.proceed(point.getArgs());

        if (StringUtils.isBlank(tokenInHeader)) throw new NeedLoginException();
        AdminToken adminToken = adminTokenMapper.selectByToken(tokenInHeader);
        if (adminToken == null) throw new NeedLoginException();
        Long adminId = adminToken.getAdminId();
        AdminAccount adminAccount = adminAccountMapper.selectByPrimaryKey(adminId);

        RequireRole requireRole = method.getAnnotation(RequireRole.class);
        if (requireRole != null) {
            Integer role = adminAccount == null || adminAccount.getRole() == null ? 99 : adminAccount.getRole();
            if (role > requireRole.value()) {
                throw new NoPermissionException("无权限访问该功能（需要角色 " + requireRole.value() + "）");
            }
        }

        AdminAccountHelper.put(adminAccount);

        return point.proceed(point.getArgs());
    }


}
