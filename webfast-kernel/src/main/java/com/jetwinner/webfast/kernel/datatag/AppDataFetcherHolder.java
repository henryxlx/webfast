package com.jetwinner.webfast.kernel.datatag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Component
public class AppDataFetcherHolder {

    private static final Logger logger = LoggerFactory.getLogger(AppDataFetcherHolder.class);

    private final ApplicationContext applicationContext;

    public AppDataFetcherHolder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initDataFetcherMap(FastDataTagRegister.dataFetcherClassNames);
    }

    private void initDataFetcherMap(List<String> fetcherClassNames) {
        try {
            for (String clazzName : fetcherClassNames) {
                Object obj = Class.forName(clazzName).getDeclaredConstructor(ApplicationContext.class)
                        .newInstance(this.applicationContext);

                if (obj instanceof BaseDataFetcher) {
                    BaseDataFetcher dataFetcher = (BaseDataFetcher) obj;
                    this.dataFetcherMap.put(dataFetcher.getName(), dataFetcher);
                }
            }
        } catch (Exception e) {
            logger.warn("DataTag fetcher create new instance error: " + e.getMessage());
        }
    }

    private Map<String, BaseDataFetcher> dataFetcherMap = new HashMap<>();

    private DummyDataFetcher dummyDataFetcher = new DummyDataFetcher(null);

    public BaseDataFetcher get(Class<?> dataTagClazz) {
        return get(dataTagClazz.getName());
    }

    public  BaseDataFetcher get(String dataTagName) {
        return dataFetcherMap.getOrDefault(dataTagName, dummyDataFetcher);
    }
}
