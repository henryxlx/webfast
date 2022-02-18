package com.jetwinner.webfast.module.service.impl;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.dao.AppArticleCategoryDao;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.module.service.AppArticleCategoryService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author xulixin
 */
@Service
public class AppArticleCategoryServiceImpl implements AppArticleCategoryService {

    private final AppArticleCategoryDao categoryDao;

    public AppArticleCategoryServiceImpl(AppArticleCategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
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

    private void makeCategoryTree(List<Map<String, Object>> tree, Map<String, List<Map<String, Object>>> categories,
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

    private Map<String, List<Map<String, Object>>> prepare(List<Map<String, Object>> categories) {
        int size = categories != null ? categories.size() : 0;
        Map<String, List<Map<String, Object>>> prepared = new HashMap<>(size);
        if (size > 0) {
            for (Map<String, Object> category : categories) {
                prepared.computeIfAbsent(String.valueOf(category.get("parentId")), k -> new ArrayList<>())
                        .add(category);
            }
        }
        return prepared;
    }

    @Override
    public List<Map<String, Object>> getCategoryTree() {
        Map<String, List<Map<String, Object>>> categories = prepare(findAllCategories());
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

    @Override
    public void createCategory(Map<String, Object> formData) {
        Map<String, Object> category = ArrayToolkit.part(formData, "name", "code", "weight",
                "parentId", "publishArticle","seoTitle","seoKeyword","seoDesc","published");

        if (!ArrayToolkit.required(formData, "name", "code", "weight", "parentId")) {
            throw new RuntimeGoingException("缺少必要参数，，添加栏目失败");
        }

        filterCategoryFields(category);

        category.put("createdTime", System.currentTimeMillis());

        category = categoryDao.addCategory(category);

        // logService.info("category", "create", String.format("添加栏目 %s(#%s)", category.get("name"), category.get("id")), category);
    }

    @Override
    public boolean isCategoryCodeAvaliable(String code, String exclude) {
        if (EasyStringUtil.isBlank(code)) {
            return false;
        }

        if (EasyStringUtil.equals(code, exclude)) {
            return true;
        }

        Map<String, Object> category = categoryDao.findCategoryByCode(code);
        return category == null;
    }

    @Override
    public Map<String, Object> getCategoryByCode(String code) {
        return categoryDao.findCategoryByCode(code);
    }

    @Override
    public void updateCategory(Integer id, Map<String, Object> updatedMap) {
        Map<String, Object> category = getCategory(id);
        if (category == null) {
            throw new RuntimeGoingException(String.format("栏目(%d)不存在，更新栏目失败！", id));
        }

        Map<String, Object> fields = ArrayToolkit.part(updatedMap, "name", "code", "weight",
                "parentId", "publishArticle", "seoTitle", "seoKeyword", "seoDesc", "published");
        if (fields == null || fields.isEmpty()) {
            throw new RuntimeGoingException("参数不正确，更新栏目失败！");
        }

        filterCategoryFields(fields);
        // logService.info("category", "update", String.format("编辑栏目 %s(#%d)", fields.get("name"), id), fields);
        categoryDao.updateCategory(id, fields);
    }

    @Override
    public int findCategoriesCountByParentId(Integer parentId) {
        return categoryDao.findCategoriesCountByParentId(parentId);
    }

    @Override
    public void deleteCategory(Integer id) {
        Map<String, Object> category = getCategory(id);
        if (category == null) {
            throw new RuntimeGoingException("资讯栏目不存在！");
        }

        Set<Object> ids = findCategoryChildrenIds(id);
        ids.add(id);
        categoryDao.deleteByIds(ids);
        // logService.info("category", "delete", String.format("删除栏目%s(#%d)", category.get("name"), id));
    }

    private void filterCategoryFields(Map<String, Object> fields) {

        ArrayToolkit.filter(fields, new ParamMap()
                .add("name", "")
                .add("code", "")
                .add("weight", 0)
                .add("publishArticle", "")
                .add("seoTitle", "")
                .add("seoDesc", "")
                .add("published", 1)
                .add("parentId", 0).toMap());

        if (EasyStringUtil.isBlank(fields.get("name"))) {
            throw new RuntimeGoingException("名称不能为空，保存栏目失败");
        }

        if (EasyStringUtil.isBlank(fields.get("code"))) {
            throw new RuntimeGoingException("编码不能为空，保存栏目失败");
        } else {
            String code = String.valueOf(fields.get("code"));
            if (!Pattern.matches("^[a-zA-Z0-9_]+$", code)) {
                throw new RuntimeGoingException(String.format("编码(%s)含有非法字符，保存栏目失败", code));
            }
            if (EasyStringUtil.isNumeric(code)) {
                throw new RuntimeGoingException(String.format("编码(%s)不能全为数字，保存栏目失败", code));
            }
        }
    }
}
