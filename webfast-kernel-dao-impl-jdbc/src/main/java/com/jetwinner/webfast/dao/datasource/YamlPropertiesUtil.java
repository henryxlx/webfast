package com.jetwinner.webfast.dao.datasource;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.support.EncodedResource;

import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * @author xulixin
 */
public class YamlPropertiesUtil {

    private YamlPropertiesUtil() {
        // reserved.
    }

    public static Properties loadYaml(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IllegalStateException e) {
            // for ignoreResourceNotFound
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException) {
                throw (FileNotFoundException) e.getCause();
            }
            throw e;
        }
    }
}
