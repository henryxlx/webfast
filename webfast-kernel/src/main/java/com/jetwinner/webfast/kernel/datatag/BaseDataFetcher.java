package com.jetwinner.webfast.kernel.datatag;

import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public abstract class BaseDataFetcher {

    protected final ApplicationContext applicationContext;

    public BaseDataFetcher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String getName() {
        String name = this.getClass().getSimpleName();
        return name != null ? name.replace("DataFetcher", "") : "nameLost";
    }

    public abstract List<Map<String, Object>> getData(Map<String, Object> arguments);
}
