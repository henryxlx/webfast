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

import java.util.Properties;

/**
 * @author xulixin
 */
@Configuration
public class FastAppDataHolderConfigurer {

    private static final Logger log = LoggerFactory.getLogger(FastAppDataHolderConfigurer.class);

    @Value("${custom.app.datadict.yml.path:datadict.yml}")
    private String appDataDictYmlPath;

    @Value("${custom.app.menu.yml.path:menu-admin.yml}")
    private String appMenuYmlPath;

    @Bean
    public FastDataDictHolder dataDictHolder() {
        Properties properties = new Properties();
        String dictPaths = "webfast-datadict.yml, " + appDataDictYmlPath;
        String[] ymlFilePaths = dictPaths.split(",");
        for (String ymlFilePath : ymlFilePaths) {
            properties.putAll(getPropertiesFromYml(ymlFilePath.trim(), "WebFast data dict yml file not found. "));
        }
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        return new Binder(source).bind("data", FastDataDictHolder.class).orElse(new FastDataDictHolder());
    }

    private Properties getPropertiesFromYml(String ymlFilePath, String logWarningHint) {
        Properties properties = new Properties(0);
        try {
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ClassPathResource(ymlFilePath));
            properties = yaml.getObject();
        } catch (Exception e) {
            log.warn(logWarningHint +  e.getMessage());
        }
        return properties;
    }

    @Bean
    public FastMenuHolder menuHolder() {
        Properties properties = getPropertiesFromYml(appMenuYmlPath,
                "WebFast menu yml file not found. ");
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        return new Binder(source).bind("menu", FastMenuHolder.class).orElse(new FastMenuHolder());
    }
}
