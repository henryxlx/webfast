package com.jetwinner.webfast.dao.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.StringEncoderUtil;
import com.jetwinner.webfast.kernel.AppWorkingConstant;
import com.jetwinner.webfast.kernel.dao.DataSourceConfig;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.InstallControllerRegisterService;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
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

    static final String DEFAULT_DATA_SOURCE_BEAN_NAME = "dataSource";
    static final String DEFAULT_TRANSACTION_BEAN_NAME = "txManager";

    private boolean dataSourceDisabled = false;

    private final AppWorkingConstant appConst;
    private final ApplicationContext applicationContext;

    public DataSourceConfigurer(AppWorkingConstant constant,
                                ApplicationContext applicationContext) {

        this.appConst = constant;
        this.applicationContext = applicationContext;
    }

    @Bean(DEFAULT_DATA_SOURCE_BEAN_NAME)
    public DataSource dataSource(InstallControllerRegisterService installControllerRegisterService) {
        DataSource dataSource = createDataSource();
        if (dataSourceDisabled) {
            try {
                installControllerRegisterService.addInstallControllerMapping();
            } catch (Exception e) {
                throw new RuntimeGoingException(e.getMessage());
            }
        }
        return dataSource;
    }

    private DataSource createDataSource() {
        try {
            dataSourceDisabled = false;
            Resource resource = new FileSystemResource(appConst.getStoragePath() + DATA_SOURCE_CONFIG_FILE_LOC);
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

    @Bean(DEFAULT_TRANSACTION_BEAN_NAME)
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

    @Override
    public void reloadDataSource() {
        DefaultListableBeanFactory beanFactory =
                (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        beanFactory.destroySingleton(DEFAULT_TRANSACTION_BEAN_NAME);
        beanFactory.destroySingleton(DEFAULT_DATA_SOURCE_BEAN_NAME);
        DataSource dataSource = createDataSource();
        beanFactory.registerSingleton(DEFAULT_DATA_SOURCE_BEAN_NAME, dataSource);
        beanFactory.registerSingleton(DEFAULT_TRANSACTION_BEAN_NAME, new DataSourceTransactionManager(dataSource));
    }
}
