package com.jetwinner.webfast.kernel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @author xulixin
 */
@Configuration
public class FastAppDataHolderConfigurer {

    private static final Logger log = LoggerFactory.getLogger(FastAppDataHolderConfigurer.class);

    @Value("${custom.app.menu.yml.path:menu-admin.yml}")
    private String appMenuYmlPath;

    @Bean
    public FastMenuHolder menuHolder() {
        Properties properties = new Properties();
        try {
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ClassPathResource(appMenuYmlPath));
            properties = yaml.getObject();
        } catch (Exception e) {
            log.warn("WebFast admin menu yml file not found. " +  e.getMessage());
        }

        MapConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        return new Binder(source).bind("menu", FastMenuHolder.class).orElse(new FastMenuHolder());
    }

    @Bean
    public FastDataDictHolder dataDictHolder() {
        Properties properties = new Properties();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:datadict.yml");
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(resources);
            properties = yaml.getObject();
        } catch (Exception e) {
            log.warn("WebFast data dictionary file(datadict.yml) not found." + e.getMessage());
        }
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        return new Binder(source).bind("data", FastDataDictHolder.class).orElse(new FastDataDictHolder());
    }
}
