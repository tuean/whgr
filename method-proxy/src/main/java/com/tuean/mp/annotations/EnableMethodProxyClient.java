package com.tuean.mp.annotations;

import com.tuean.mp.config.MethodProxyClientConfiguration;
import com.tuean.mp.util.SpringContextUtil;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

import java.lang.annotation.*;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MethodProxyClientConfiguration.class, SpringContextUtil.class})
@Documented
@Role(ROLE_INFRASTRUCTURE)
public @interface EnableMethodProxyClient {


}
