package com.tuean.mp.annotations;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodProxyCall {

    String methodName() default "";

    String target() default "";

    boolean proxyAll() default true;

}
