package com.jetwinner.webfast.module.bigapp.service.impl;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.MapUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.module.bigapp.dao.AppCategoryDao;
import com.jetwinner.webfast.module.bigapp.dao.AppCategoryGroupDao;
import com.jetwinner.webfast.module.bigapp.service.AppCategoryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author xulixin
 */
@Service
public class AppCategoryServiceImpl implements AppCategoryService {

    private final AppCategoryDao categoryDao;
    private final AppCategoryGroupDao groupDao;
    private final AppLogService logService;

    public AppCategoryServiceImpl(AppCategoryDao categoryDao, AppCategoryGroupDao groupDao, AppLogService logService) {
        this.categoryDao = categoryDao;
        this.groupDao = groupDao;
        this.logService = logService;
    }

    public Map<String, Object> getCategory(Object id) {
        return categoryDao.getCategory(id);
    }

    @Override
    public Map<String, Map<String, Object>> findCategoriesByIds(Set<Object> categoryIds) {
        return ArrayToolkit.index(categoryDao.findByIds(categoryIds), "id");
    }

    private void filterCategoryFields(Map<String, Object> category, Map<String, Object> releatedCategory) {
        for (String key : category.keySet()) {
            switch (key) {
                case "name":
                    if (EasyStringUtil.isBlank(category.get("name"))) {
                        throw new RuntimeGoingException("名称不能为空，保存分类失败");
                    }
                    break;
                case "code":
                    if (EasyStringUtil.isBlank(category.get("code"))) {
                        throw new RuntimeGoingException("编码不能为空，保存分类失败");
                    } else {
                        String code = String.valueOf(category.get("code"));
                        if (!Pattern.matches("^[a-zA-Z0-9_]+$", code)) {
                            throw new RuntimeGoingException(String.format("编码(%s)含有非法字符，保存分类失败", code));
                        }
                        if (EasyStringUtil.isNumeric(code)) {
                            throw new RuntimeGoingException(String.format("编码(%s)不能全为数字，保存分类失败", code));
                        }
                        String exclude = releatedCategory == null || releatedCategory.get("code") == null ? null :
                                String.valueOf(releatedCategory.get("code"));
                        if (!isCategoryCodeAvaliable(code, exclude)) {
                            throw new RuntimeGoingException(String.format("编码(%s)不可用，保存分类失败", code));
                        }
                    }
                    break;
                case "groupId":
                    Map<String, Object> group = getGroup(category.get("groupId"));
                    if (group == null || group.isEmpty()) {
                        throw new RuntimeGoingException(String.format("分类分组ID(%s)不存在，保存分类失败",
                                category.get("groupId")));
                    }
                    break;
                case "parentId":
                    int categoryParentId = ValueParser.parseInt(category.get("parentId"));
                    if (categoryParentId > 0) {
                        Map<String, Object> parentCategory = getCategory(categoryParentId);
                        if (parentCategory == null ||
                                ValueParser.parseInt(parentCategory.get("groupId")) != categoryParentId) {

                            throw new RuntimeGoingException(String.format("父分类(ID:%s)不存在，保存分类失败",
                                    category.get("groupId")));
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void createCategory(AppUser currentUser, Map<String, Object> categoryMap) {
        Map<String, Object> category = ArrayToolkit.part(categoryMap,
                "description", "name", "code", "weight", "groupId", "parentId", "icon");

        if (!ArrayToolkit.required(category, "name", "code", "weight", "groupId", "parentId")) {
            throw new RuntimeGoingException("缺少必要参数，，添加分类失败");
        }

        filterCategoryFields(category, null);
        category = categoryDao.addCategory(category);

        logService.info(currentUser, "category", "create",
                String.format("添加分类 %s(#%s)", category.get("name"), category.get("id")));
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

    public boolean isCategoryCodeAvaliable(String code, String exclude) {
        if (EasyStringUtil.isBlank(code)) {
            return false;
        }

        if (Objects.equals(code, exclude)) {
            return true;
        }

        Map<String, Object> category = categoryDao.findCategoryByCode(code);
        return category != null && category.size() > 0 ? false : true;
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
    public Map<String, Object> addGroup(Map<String, Object> group) {
        return groupDao.addGroup(group);
    }

    public Map<String, Object> getGroup(Object id) {
        return groupDao.getGroup(id);
    }

    @Override
    public Map<String, Object> getGroupByCode(String code) {
        return groupDao.findGroupByCode(code);
    }
}
