package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppTagDao {

    List<Map<String, Object>> findTagsByNames(String[] names);

    Map<String, Object> addTag(Map<String, Object> tagMap);

    Map<String, Object> getTagByName(String name);

    List<Map<String, Object>> getTagByLikeName(String partOfName);
}
