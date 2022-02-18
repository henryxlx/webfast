package com.jetwinner.webfast.module.service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppTagService {

    List<Map<String, Object>> findTagsByNames(String[] names);

    Map<String, Object> getTagByName(String name);

    void addTag(Map<String, Object> fields);

    List<Map<String, Object>> getTagByLikeName(String partOfName);
}
