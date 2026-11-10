package com.tuean.whgr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法级权限：要求当前账号的 role <= value（role 越小权限越大：1 管理员 > 2 普通用户）。
 * 不写这个注解表示登录即可访问；@NoAccess 表示不需要登录。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {

    int value() default 1;

}
