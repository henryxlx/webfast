package com.jetwinner.webfast.kernel.service.content.type;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xulixin
 */
@Component
public class ContentTypeFactory {

    List<BaseContentType> contentTypeList;

    public ContentTypeFactory(List<BaseContentType> contentTypeList) {
        this.contentTypeList = contentTypeList;
        if (!CollectionUtils.isEmpty(contentTypeList)) {
            contentTypeList.forEach(e -> cachedContentTypeMap.put(e.getAlias(), e));
        }
    }

    private static ConcurrentHashMap<String, BaseContentType> cachedContentTypeMap = new ConcurrentHashMap<>();

    public static BaseContentType create(String alias) {
        if (cachedContentTypeMap.containsKey(alias)) {
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
