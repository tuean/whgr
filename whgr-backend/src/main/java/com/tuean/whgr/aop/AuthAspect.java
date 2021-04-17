package com.tuean.whgr.aop;


import com.tuean.whgr.annotation.NoAccess;
import com.tuean.whgr.dao.AdminAccountMapper;
import com.tuean.whgr.dao.AdminTokenMapper;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.entity.db.AdminToken;
import com.tuean.whgr.exception.NeedLoginException;
import com.tuean.whgr.helper.AdminAccountHelper;
import org.apache.commons.lang.StringUtils;
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

import static com.tuean.whgr.Const.TOKEN;

@Component
@Aspect
@Order(10)
public class AuthAspect {

    private static Logger logger = LoggerFactory.getLogger(AuthAspect.class);

    @Pointcut("execution(public * com.tuean.whgr.func.*.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
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
        AdminAccountHelper.put(adminAccount);

        return point.proceed(point.getArgs());
    }


}
