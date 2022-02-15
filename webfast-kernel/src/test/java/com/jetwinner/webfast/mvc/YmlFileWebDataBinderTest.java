package com.jetwinner.webfast.mvc;

import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.WebDataBinder;

import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class YmlFileWebDataBinderTest {

    private static final String menuDefaultPrefix = "menu.";

    @Test
    public void testLoadYmlFile() {
        Properties propForMenu = null;
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        try {
            yaml.setResources(new ClassPathResource("menu-admin-test.yml"));
            propForMenu = yaml.getObject();
        } catch (Exception e) {
            // do nothing.
        }
        MenuHolder menuHolder = new MenuHolder();
        if (propForMenu != null && propForMenu.size() > 0) {
            bind(propForMenu, menuHolder);
        }
        if (propForMenu == null) {
            assertTrue("yml file not found.", menuHolder.getAdminTop() == null);
        } else {
            assertTrue("bind success!", menuHolder.getAdminTop() != null);
        }
    }

    private void bind(Properties propForMenu, MenuHolder menuHolder) {
        WebDataBinder dataBinder = new WebDataBinder(menuHolder);
        dataBinder.setIgnoreInvalidFields(true);
        dataBinder.setIgnoreUnknownFields(true);
        dataBinder.setFieldDefaultPrefix(menuDefaultPrefix);
        MutablePropertyValues pvs = new MutablePropertyValues(propForMenu);
        dataBinder.bind(pvs);
    }

}