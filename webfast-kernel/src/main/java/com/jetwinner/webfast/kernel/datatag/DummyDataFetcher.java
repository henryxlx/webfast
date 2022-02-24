package com.jetwinner.webfast.kernel.datatag;

import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public class DummyDataFetcher extends BaseDataFetcher {

    public DummyDataFetcher(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public String getName() {
        return DummyDataFetcher.class.getCanonicalName();
    }

    @Override
    public List<Map<String, Object>> getData(Map<String, Object> arguments) {
        return Collections.emptyList();
    }
}
