package config;

import annotations.MethodProxy;
import netty.ServerConfig;
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

/**
 * copy from https://blog.wangqi.love/articles/Java/%E5%8A%A8%E6%80%81%E6%B3%A8%E5%86%8Cbean(ImportBeanDefinitionRegistrar,%20FactoryBean).html
 */
@Configuration(
        proxyBeanMethods = false
)
public class MethodProxyConfiguration implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private static final int DEFAULT_PORT = 8888;

    private ServerConfig serverConfig;

    private ResourceLoader resourceLoader;

    private Environment environment;

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
        ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
        // 获取Class的扫描器
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);

        // 指定只扫描标注了@HttpUtil注解的接口
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(MethodProxy.class);
        scanner.addIncludeFilter(annotationTypeFilter);

        // 指定扫描的基础包
        String basePackage = ClassUtils.getPackageName(importingClassMetadata.getClassName());

        // 获取基础包底下所有符合条件的类的定义
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();

                Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(MethodProxy.class.getCanonicalName());
                // 调用registerHttpClient注册类定义
                registerHttpClient(registry, annotationMetadata, attributes);
            }
        }
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isInterface()) {
                    return !beanDefinition.getMetadata().isAnnotation();
                }
                return false;
            }
        };
    }

    private void registerHttpClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        // 在HttpFactoryBean中设置url type等信息
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(ServerConfig.class);
        definition.addPropertyValue("port", DEFAULT_PORT);
        definition.addPropertyValue("type", className);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, null);
        // 针对每一个接口注册一个ServerConfig
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    public ServerConfig getConfig() {
        return serverConfig;
    }

    public void setConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }


}
