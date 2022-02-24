package com.jetwinner.webfast.kernel.datatag;

import com.jetwinner.webfast.kernel.datatag.annotations.FastDataTag;
import com.jetwinner.webfast.kernel.datatag.annotations.FastDataTagScan;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.*;

/**
 * @author xulixin
 */
public class FastDataTagRegister implements ImportBeanDefinitionRegistrar {

    static List<String> dataFetcherClassNames = new ArrayList<>(0);

    void setDataFetcherClassNames(List<String> list) {
        dataFetcherClassNames = list;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerDataFetcher(importingClassMetadata);
    }

    public void registerDataFetcher(AnnotationMetadata metadata) {
        Set<String> basePackages = getBasePackages(metadata);
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        // 添加扫描规律规则，这里指定了内置的注解过滤规则
        provider.addIncludeFilter(new AnnotationTypeFilter(FastDataTag.class));
        // 获取扫描结果的全限定类名
        List<String> classNames = new ArrayList<>();
        // 扫描指定包，如果有多个包，这个过程可以执行多次
        for (String aPackage : basePackages) {
            Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(aPackage);
            beanDefinitionSet.forEach(beanDefinition -> classNames.add(beanDefinition.getBeanClassName()));
        }
        if (!classNames.isEmpty()) {
            setDataFetcherClassNames(classNames);
        }
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(FastDataTagScan.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (pkg != null && !"".equals(pkg)) {
                basePackages.add(pkg);
            }
        }

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

}