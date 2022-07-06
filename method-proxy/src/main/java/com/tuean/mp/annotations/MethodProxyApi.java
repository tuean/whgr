package com.tuean.mp.annotations;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodProxyApi {

    String methodName() default "";



}
