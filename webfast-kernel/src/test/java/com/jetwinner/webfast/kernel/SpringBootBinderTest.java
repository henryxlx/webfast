package com.jetwinner.webfast.kernel;

import org.junit.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

import static org.junit.Assert.assertTrue;

@Deprecated
public class SpringBootBinderTest {

    @Test
    public void testForDataDictYmlBinder() {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("datadict-test.yml"));
        Properties properties = yaml.getObject();
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(source);
        DataDictHolder dictHolder = binder.bind("data", DataDictHolder.class).get();
        if (properties == null) {
            assertTrue("yml file not found.", dictHolder.getDict() == null);
        } else {
            assertTrue("bind success!", dictHolder.getDict() != null);
        }
    }

    @Test
    public void testForAdminMenuYmlBinder() {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("menu-admin-test.yml"));
        Properties properties = yaml.getObject();
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(source);
        FastMenuHolder menuHolder = binder.bind("menu", FastMenuHolder.class).get();
        if (properties == null) {
            assertTrue("yml file not found.", menuHolder.getAdminTop() == null);
        } else {
            assertTrue("bind success!", menuHolder.getAdminTop() != null);
        }
    }
}
