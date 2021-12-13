package com.jetwinner.webfast.dao.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.jetwinner.webfast.kernel.dao.DataSourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author xulixin
 */
@Configuration
public class DataSourceConfigurer implements DataSourceConfig {

    private boolean dataSourceNotConfiguredProperly = false;

    @Value("${custom.app.storage.path}")
    private String appStoragePath;

    @Bean
    public DataSource dataSource(){
        try {
            dataSourceNotConfiguredProperly = false;
            Resource resource = new FileSystemResource(appStoragePath + "/datasource.yml");
            Properties properties = YamlPropertiesUtil.loadYaml(new EncodedResource(resource, "UTF-8"));
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setConnectProperties(properties);
            return dataSource;
        } catch (Exception e) {
            dataSourceNotConfiguredProperly = true;
            return DummyDateSource.getInstance();
        }
    }

    @Override
    public boolean isDataSourceNotConfiguredProperly() {
        return dataSourceNotConfiguredProperly;
    }

    @Override
    public void setDataSourceNotConfiguredProperly(boolean dataSourceNotConfiguredProperly) {
        this.dataSourceNotConfiguredProperly = dataSourceNotConfiguredProperly;
    }
}
