package com.tuean.mp.config;

import com.tuean.mp.annotations.MethodProxyApi;
import com.tuean.mp.annotations.MethodProxyCall;
import com.tuean.mp.util.Util;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration(
        proxyBeanMethods = false
)
public class MethodProxyClientConfiguration implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private static Logger logger = LoggerFactory.getLogger(MethodProxyClientConfiguration.class);

    private Environment environment;

    private ResourceLoader resourceLoader;

    private Map<String, Object> beanMap;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        try {
            ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
            // 获取Class的扫描器
            ClassPathScanningCandidateComponentProvider scanner = Util.getScanner(environment);
            scanner.setResourceLoader(this.resourceLoader);

            // 指定只扫描标注了@MethodProxyApi注解的接口
            AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(MethodProxyCall.class);
            scanner.addIncludeFilter(annotationTypeFilter);

            // 指定扫描的基础包
            String basePackage = ClassUtils.getPackageName(importingClassMetadata.getClassName());

            // 获取基础包底下所有符合条件的类的定义
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            int size = candidateComponents.size();
            beanMap = new ConcurrentHashMap<>(size);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();

                    Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(MethodProxyApi.class.getCanonicalName());


                }
            }
        } catch (Exception var) {
            logger.error("start client error", var);
        }
    }



}
