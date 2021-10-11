package annotations;


import config.MethodProxyConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MethodProxyConfiguration.class})
@Documented
public @interface MethodProxy {



}
