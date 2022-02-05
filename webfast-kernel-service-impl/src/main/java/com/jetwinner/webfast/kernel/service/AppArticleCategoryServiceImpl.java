package com.jetwinner.webfast.kernel.service;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.dao.AppArticleCategoryDao;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xulixin
 */
@Service
public class AppArticleCategoryServiceImpl implements AppArticleCategoryService {

    private AppArticleCategoryDao categoryDao;

    public AppArticleCategoryServiceImpl(AppArticleCategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public Map<String, Object> getCategory(Object id) {
        if (id == null) {
            return null;
        }
        return categoryDao.getCategory(id);
    }

    @Override
    public Map<String, Map<String, Object>> findCategoriesByIds(Set<Object> ids) {
        return ArrayToolkit.index(categoryDao.findCategoriesByIds(ids), "id");
    }

    public List<Map<String, Object>> findAllCategories() {
        return categoryDao.findAllCategories();
    }

    private void makeCategoryTree(List<Map<String, Object>> tree, Map<Integer, List<Map<String, Object>>> categories,
                                  int parentId, int depth) {

        if (categories.get(parentId) instanceof List) {
            for (Map<String, Object> category : categories.get(parentId)) {
                depth++;
                category.put("depth", depth);
                tree.add(category);
                makeCategoryTree(tree, categories, ValueParser.parseInt(category.get("id")), depth);
                depth--;
            }
        }
    }

    private Map<Integer, List<Map<String, Object>>> prepare(List<Map<String, Object>> categories) {
        Map<Integer, List<Map<String, Object>>> prepared = new HashMap<>();
        for (Map<String, Object> category : categories) {
            if (prepared.get(category.get("parentId")) == null) {
                prepared.put(ValueParser.createInteger(category.get("parentId")), new ArrayList<>());
            }
            prepared.get(category.get("parentId")).add(category);
        }
        return prepared;
    }

    @Override
    public List<Map<String, Object>> getCategoryTree() {
        Map<Integer, List<Map<String, Object>>> categories = prepare(findAllCategories());
        List<Map<String, Object>> tree = new ArrayList<>();
        makeCategoryTree(tree, categories, 0, 0);
        return tree;
    }

    @Override
    public Set<Object> findCategoryChildrenIds(Object id) {
        Map<String, Object> category = getCategory(id);
        if (category == null || category.isEmpty()) {
            return new HashSet<>(0);
        }
        List<Map<String, Object>> tree = getCategoryTree();

        Set<Object> childrenIds = new HashSet<>();
        int depth = 0;

        for (Map<String, Object> node : tree) {
            int nodeDepth = ValueParser.parseInt(node.get("depth"));
            if (Objects.equals(node.get("id"), category.get("id"))) {
                depth = nodeDepth;
                continue;
            }
            if (depth > 0 && depth < nodeDepth) {
                childrenIds.add(node.get("id"));
            }

            if (depth > 0 && depth >= nodeDepth) {
                break;
            }
        }

        return childrenIds;
    }
}
