package annotations;

import config.MethodProxyConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

import java.lang.annotation.*;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MethodProxyConfiguration.class})
@Documented
@Role(ROLE_INFRASTRUCTURE)
public @interface EnableMethodProxy {

    String[] basePacks() default {""};

}
