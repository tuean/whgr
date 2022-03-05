package com.tuean.whgr.aop;


import com.tuean.whgr.annotation.DBRouter;
import com.tuean.whgr.config.RoutableDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Order(73)
public class DBSelectorAspect {

    private static Logger logger = LoggerFactory.getLogger(ListParamSetAspect.class);


    @Pointcut("execution(public * com.tuean.whgr.func.*.*.*(..))")
    private void pointcut() {

    }

    @Around("pointcut() || @annotation(com.tuean.whgr.annotation.DBRouter)")
    public void before(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        DBRouter router = method.getAnnotation(DBRouter.class);
        if (router != null) {
            String targetDB = router.value();
            RoutableDataSource.set(targetDB);
        }

        point.proceed(point.getArgs());
    }


}
