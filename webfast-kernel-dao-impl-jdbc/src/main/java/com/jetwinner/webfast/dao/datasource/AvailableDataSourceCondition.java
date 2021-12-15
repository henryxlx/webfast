package com.jetwinner.webfast.dao.datasource;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.sql.DataSource;

/**
 * @author xulixin
 */
@Deprecated
public class AvailableDataSourceCondition implements ConfigurationCondition {

    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return ConfigurationPhase.REGISTER_BEAN;
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        boolean availableDataSource =  beanFactory.getBeansOfType(DummyDateSource.class).isEmpty();
        return availableDataSource;
    }
}
