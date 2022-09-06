package com.tuean.mp.config;

import com.tuean.mp.annotations.MethodProxyApi;
import com.tuean.mp.netty.ServerConfig;
import com.tuean.mp.netty.server.InnerServer;
import com.tuean.mp.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.*;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * copy from https://blog.wangqi.love/articles/Java/%E5%8A%A8%E6%80%81%E6%B3%A8%E5%86%8Cbean(ImportBeanDefinitionRegistrar,%20FactoryBean).html
 */
@Configuration(
        proxyBeanMethods = false
)
public class MethodProxyServerConfiguration implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private static Logger logger = LoggerFactory.getLogger(MethodProxyServerConfiguration.class);

    public static final int DEFAULT_PORT = 8888;

//    private ServerConfig serverConfig;

    private ResourceLoader resourceLoader;

    private Environment environment;

    private Map<String, String> beanMap;

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
            AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(MethodProxyApi.class);
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
                    // 调用registerHttpClient注册类定义
                    registerHttpClient(registry, annotationMetadata, attributes, beanMap);
                }
            }

            InnerServer server = new InnerServer(Util.getByEnv(environment));
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(server);
        } catch (Exception var) {
            logger.error("start server error", var);
        }
    }



    private void registerHttpClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, Map<String, Object> attributes, Map<String, String> beanMap) {
        String className = annotationMetadata.getClassName();
        String beanName = className + "$MethodProxy";
        // 在HttpFactoryBean中设置url type等信息
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(ServerConfig.class);
//        definition.addPropertyValue("port", DEFAULT_PORT);
//        definition.addPropertyValue("type", className);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);

        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, beanName, null);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);

        // 将接口注册到beanMap中
        beanMap.put(className, beanName);
    }


}
