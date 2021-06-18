package com.tuean.whgr.ws;

import cn.hutool.core.util.ArrayUtil;
import com.tuean.whgr.annotation.WsMapping;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

@Component
public class MethodScan implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent()==null){
            Map<String,Object> beans = event.getApplicationContext().getBeansWithAnnotation(WsMapping.class);
            for(Object bean:beans.values()){
                System.err.println(bean==null?"null":bean.getClass().getName());
                Method[] methods = bean.getClass().getMethods();
                if (ArrayUtil.isEmpty(methods)) continue;
                Arrays.asList(methods).forEach(method -> {
                    if (method.isAnnotationPresent(WsMapping.class)) {
//                        Annotation[] annotations = method.getAnnotations();
                        WsMapping wsMapping = method.getAnnotation(WsMapping.class);

                    }
                });

            }
            System.err.println("=====ContextRefreshedEvent====="+event.getSource().getClass().getName());
        }
    }
}
