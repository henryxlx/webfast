package com.jetwinner.webfast.module.bigapp.service;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppTagService {

    List<Map<String, Object>> findTagsByNames(String[] names);

    Map<String, Object> getTagByName(String name);

    Map<String, Object> addTag(AppUser currentUser, Map<String, Object> fields);

    List<Map<String, Object>> getTagByLikeName(String partOfName);

    List<Map<String, Object>> findTagsByIds(String[] ids);

    int getAllTagCount();

    List<Map<String, Object>> findAllTags(Integer start, Integer limit);

    Map<String, Object> getTag(Integer id);

    Map<String, Object> updateTag(AppUser currentUser, Integer id, Map<String, Object> toUpdateDataMap);

    int deleteTag(AppUser currentUser, Integer id);

    boolean isTagNameAvailable(String name, String exclude);
}
