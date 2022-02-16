package com.jetwinner.webfast.kernel.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.WebDataBinder;

import java.util.Properties;

/**
 * @author xulixin
 */
@Configuration
public class MenuConfigurer {

    private static final Logger log = LoggerFactory.getLogger(MenuConfigurer.class);

    @Value("${custom.app.menu.yml.path:menu-admin.yml}")
    private String appMenuYmlPath;

    private String menuDefaultPrefix = "menu.";

    @Bean
    public MenuHolder menuProperties() {
        Properties propForMenu = null;
        try {
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ClassPathResource(appMenuYmlPath));
            propForMenu = yaml.getObject();
        } catch (Exception e) {
            log.warn("WebFast admin menu yml file not found. " +  e.getMessage());
        }
        MenuHolder menuHolder = new MenuHolder();
        if (propForMenu != null && propForMenu.size() > 0) {
            bindMenuHolder(menuHolder, propForMenu);
        }
        return menuHolder;
    }

    private void bindMenuHolder(MenuHolder menuHolder, Properties propForMenu) {
        WebDataBinder dataBinder = new WebDataBinder(menuHolder);
        dataBinder.setIgnoreInvalidFields(true);
        dataBinder.setIgnoreUnknownFields(true);
        dataBinder.setFieldDefaultPrefix(menuDefaultPrefix);
        MutablePropertyValues pvs = new MutablePropertyValues(propForMenu);
        dataBinder.bind(pvs);
    }
}
