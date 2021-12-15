package com.jetwinner.webfast.dao.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.jetwinner.webfast.kernel.dao.DataSourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author xulixin
 */
@Configuration
public class DataSourceConfigurer implements DataSourceConfig {

    private boolean dataSourceDisabled = false;

    @Value("${custom.app.storage.path}")
    private String appStoragePath;

    @Bean
    public DataSource dataSource(){
        try {
            dataSourceDisabled = false;
            Resource resource = new FileSystemResource(appStoragePath + "/datasource.yml");
            Properties properties = YamlPropertiesUtil.loadYaml(new EncodedResource(resource, "UTF-8"));
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setConnectProperties(properties);
            return dataSource;
        } catch (Exception e) {
            dataSourceDisabled = true;
            return DummyDateSource.getInstance();
        }
    }

    @Bean("txManager")
    public DataSourceTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public boolean getDataSourceDisabled() {
        return dataSourceDisabled;
    }

    @Override
    public void setDataSourceDisabled(boolean dataSourceDisabled) {
        this.dataSourceDisabled = dataSourceDisabled;
    }
}
