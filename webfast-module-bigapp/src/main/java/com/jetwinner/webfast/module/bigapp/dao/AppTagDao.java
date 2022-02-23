package com.jetwinner.webfast.module.bigapp.dao;

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

    List<Map<String, Object>> findTagsByIds(String[] ids);

    int findAllTagsCount();

    List<Map<String, Object>> findAllTags(Integer start, Integer limit);

    Map<String, Object> getTag(Integer id);

    int updateTag(Integer id, Map<String, Object> fields);

    int deleteTag(Integer id);
}
