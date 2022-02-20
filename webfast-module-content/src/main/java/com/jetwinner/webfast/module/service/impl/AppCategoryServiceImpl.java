package com.jetwinner.webfast.module.service.impl;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.MapUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.module.dao.AppCategoryDao;
import com.jetwinner.webfast.module.dao.AppCategoryGroupDao;
import com.jetwinner.webfast.module.service.AppCategoryService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xulixin
 */
@Service
public class AppCategoryServiceImpl implements AppCategoryService {

    private final AppCategoryDao categoryDao;
    private final AppCategoryGroupDao groupDao;

    public AppCategoryServiceImpl(AppCategoryDao categoryDao, AppCategoryGroupDao groupDao) {
        this.categoryDao = categoryDao;
        this.groupDao = groupDao;
    }

    public Map<String, Object> getCategory(Object id) {
        return categoryDao.getCategory(id);
    }

    @Override
    public Map<String, Map<String, Object>> findCategoriesByIds(Set<Object> categoryIds) {
        return ArrayToolkit.index(categoryDao.findByIds(categoryIds), "id");
    }

    @Override
    public void createCategory(Map<String, Object> categoryMap) {

    }

    private Map<String, List<Map<String, Object>>> prepare(List<Map<String, Object>> categories) {
        Map<String, List<Map<String, Object>>> prepared = new HashMap<>();
        for (Map<String, Object> category : categories) {
            prepared.computeIfAbsent(String.valueOf(category.get("parentId")), k -> new ArrayList<>()).add(category);
        }
        return prepared;
    };

    @Override
    public List<Map<String, Object>> getCategoryTree(Object groupId) {
        Map<String, Object> group = getGroup(groupId);
        if (group == null || group.isEmpty()) {
            throw new RuntimeGoingException(String.format("分类Group #%s，不存在", groupId));
        }
        Map<String, List<Map<String, Object>>> categories = prepare(findCategories(groupId));
        List<Map<String, Object>> tree = new ArrayList<>();
        makeCategoryTree(tree, categories, 0, 0);
        return tree;
    }

    private void makeCategoryTree(List<Map<String, Object>> tree,
                                  Map<String, List<Map<String, Object>>> categories,
                                  int parentId, int depth) {

        String strParentId = String.valueOf(parentId);
        if (categories.get(strParentId) != null) {
            for (Map<String, Object> category : categories.get(strParentId)) {
                depth++;
                category.put("depth", depth);
                tree.add(category);
                makeCategoryTree(tree, categories, ValueParser.parseInt(category.get("id")), depth);
                depth--;
            }
        }
    }

    public List<Map<String, Object>> findCategories(Object groupId) {
        Map<String, Object> group = getGroup(groupId);
        if (group == null || group.isEmpty()) {
            // throw new RuntimeGoingException(String.format("分类Group #%s，不存在", groupId));
            group.put("id", 1);
        }
        return categoryDao.findCategoriesByGroupId(group.get("id"));
    }

    @Override
    public Set<Object> findCategoryChildrenIds(Object categoryId) {
        Map<String, Object> category = getCategory(categoryId);
        if (MapUtil.isEmpty(category)) {
            return new HashSet<>(0);
        }
        List<Map<String, Object>> tree = getCategoryTree(category.get("groupId"));

        Set<Object> childrenIds = new HashSet<>();
        int depth = 0;
        for (Map<String, Object> node : tree) {
            int node_get_depth_value = ValueParser.parseInt(node.get("depth"));
            if (Objects.equals(node.get("id"), category.get("id"))) {
                depth = node_get_depth_value;
                continue;
            }
            if (depth > 0 && depth < node_get_depth_value) {
                childrenIds.add(node.get("id"));
            }

            if (depth > 0 && depth >= node_get_depth_value) {
                break;
            }
        }

        return childrenIds;
    }

    /*
     * Group
     */

    @Override
    public Map<String, Object> addGroup(Map<String, Object> groupMap) {
        return null;
    }

    public Map<String, Object> getGroup(Object id) {
        return groupDao.getGroup(id);
    }

    @Override
    public Map<String, Object> getGroupByCode(String code) {
        return groupDao.findGroupByCode(code);
    }
}
