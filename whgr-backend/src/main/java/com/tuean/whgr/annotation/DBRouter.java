package com.tuean.whgr.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DBRouter {

    String value() default "";

}
