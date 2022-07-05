package com.tuean.whgr.aop;

import com.tuean.whgr.entity.BaseListRequest;
import com.tuean.whgr.entity.PageTair;
import com.tuean.whgr.util.PageUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(Integer.MIN_VALUE)
public class ListParamSetAspect {

    private static Logger logger = LoggerFactory.getLogger(ListParamSetAspect.class);


    @Pointcut("execution(public * com.tuean.whgr.func.*.*.*(..))")
    private void pointcut(){}


    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        if (args == null || args.length < 1) return point.proceed(args);

        for (Object a : args) {
            if (a instanceof BaseListRequest) {
                BaseListRequest listRequest = (BaseListRequest) a;
                PageTair tair = PageUtils.transferTo(listRequest.getPageNum(), listRequest.getPageSize());
                Integer startRow = tair.getStartRow() == -1 ? null : tair.getStartRow();
                Integer endRow = tair.getEndRow() == -1 ? null : tair.getEndRow();

                listRequest.setStartRow(startRow);
                listRequest.setEndRow(endRow);
            }
        }
        return point.proceed(args);
    }

}
