package com.jetwinner.webfast.dao.datasource;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.sql.DataSource;

/**
 * @author xulixin
 */
public class AvailableDataSourceCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        DataSource dataSource = context.getBeanFactory().getBean(DataSource.class);
        if (dataSource != null && !(dataSource instanceof DummyDateSource)) {
            return true;
        }
        return false;
    }
}
