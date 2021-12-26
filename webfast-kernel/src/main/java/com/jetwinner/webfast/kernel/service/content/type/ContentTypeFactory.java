package com.jetwinner.webfast.kernel.service.content.type;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xulixin
 */
public class ContentTypeFactory {

    private static ConcurrentHashMap<String, BaseContentType> cachedContentTypeMap = new ConcurrentHashMap<>();

    public static BaseContentType create(String alias) {
        if (cachedContentTypeMap.contains(alias)) {
            return cachedContentTypeMap.get(alias);
        }
        return new BaseContentType() {
            @Override
            public String getAlias() {
                return "";
            }

            @Override
            public String getName() {
                return "";
            }
        };
    }
}
