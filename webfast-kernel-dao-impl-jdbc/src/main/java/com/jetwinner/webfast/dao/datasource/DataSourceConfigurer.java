package com.jetwinner.webfast.dao.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.StringEncoderUtil;
import com.jetwinner.webfast.kernel.AppWorkingConstant;
import com.jetwinner.webfast.kernel.dao.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.Set;

/**
 * @author xulixin
 */
@Configuration
public class DataSourceConfigurer implements DataSourceConfig {

    private boolean dataSourceDisabled = false;

    private final AppWorkingConstant appConst;

    public DataSourceConfigurer(AppWorkingConstant constant) {
        this.appConst = constant;
    }

    @Bean
    public DataSource dataSource(){
        try {
            dataSourceDisabled = false;
            Resource resource = new FileSystemResource(appConst.getStoragePath() + "/datasource.yml");
            Properties properties = YamlPropertiesUtil.loadYaml(new EncodedResource(resource, AppWorkingConstant.CHARSET_UTF8));
            Set<Object> keys = properties.keySet();
            keys.forEach(k -> {
                if (EasyStringUtil.containsAny(k.toString(), "url", "username", "password")) {
                    properties.put(k, StringEncoderUtil.decode(properties.get(k)));
                }
            });
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
