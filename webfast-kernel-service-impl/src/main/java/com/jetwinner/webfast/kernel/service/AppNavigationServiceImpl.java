package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.dao.AppNavigationDao;
import com.jetwinner.webfast.kernel.model.AppModelNavigation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Service
public class AppNavigationServiceImpl implements AppNavigationService {

    private final AppNavigationDao navigationDao;

    public AppNavigationServiceImpl(AppNavigationDao navigationDao) {
        this.navigationDao = navigationDao;
    }

    @Override
    public void createNavigation(Map<String, Object> model) {
        long now = System.currentTimeMillis();
        model.put("createdTime", now);
        model.put("updateTime", now);
        model.put("sequence", navigationDao.getNavigationsCountByType(model.get("type").toString()) + 1);
        navigationDao.insert(model);
    }

    public int countNavigationsByType(String type) {
        return navigationDao.countNavigationsByType(type);
    }

    public List<AppModelNavigation> findAllByType(String type, int start, int count) {
        return navigationDao.findAllByType(type, start, count);
    }

    @Override
    public List<AppModelNavigation> getNavigationsListByType(String type) {
        int count = countNavigationsByType(type);
        List<AppModelNavigation> preparedList = findAllByType(type, 0, count);

        Map<Integer, List<AppModelNavigation>> navigations = new HashMap<>();
        for (AppModelNavigation nav : preparedList) {
            if (!navigations.containsKey(nav.getParentId())) {
                navigations.put(nav.getParentId(), new ArrayList<>());
            }
            navigations.get(nav.getParentId()).add(nav);
        }

        List<AppModelNavigation> tree = new ArrayList<>();
        makeNavigationTreeList(tree, navigations, 0);
        return tree;
    }

    @Override
    public List<AppModelNavigation> findNavigationsByType(String type, int start, int limit) {
        return navigationDao.findAllByType(type, start, limit);
    }

    @Override
    public int deleteNavigation(Integer id) {
        return navigationDao.deleteById(id) + navigationDao.deleteByParentId(id);
    }

    @Override
    public AppModelNavigation getNavigationById(Integer id) {
        return navigationDao.getById(id);
    }

    @Override
    public int updateNavigation(Integer id, Map<String, Object> fields) {
        fields.put("id", id);
        fields.put("updateTime", System.currentTimeMillis());

        // logService.info("info", "navigation_update", String.format("编辑导航#%d", id), fields);

        return navigationDao.updateNavigation(fields);
    }

    @Override
    public List<AppModelNavigation> getNavigationsTreeByType(String type) {
        int count = countNavigationsByType(type);
        List<AppModelNavigation> navigationList = findNavigationsByType(type, 0, count);
        Map<Integer, AppModelNavigation> map = navigationList.stream()
                .collect(Collectors.toMap(AppModelNavigation::getId, Function.identity()));

        List<AppModelNavigation> rootList = new ArrayList<>();
        for (AppModelNavigation nav : navigationList) {
            if (nav.getParentId() > 0) {
                if (map.get(nav.getParentId()).getChildren() == null) {
                    map.get(nav.getParentId()).setChildren(new ArrayList<>());
                }

                if (nav.getIsOpen() > 0) {
                    map.get(nav.getParentId()).getChildren().add(map.get(nav.getId()));
                    map.remove(nav.getId());
                }
            } else {
                rootList.add(map.get(nav.getId()));
            }

        }
        return rootList;
    }

    private void makeNavigationTreeList(List<AppModelNavigation> tree,
                                        Map<Integer, List<AppModelNavigation>> navigations, int parentId) {

        List<AppModelNavigation> subNavs = navigations.get(parentId);
        if (subNavs != null) {
            for (AppModelNavigation nav : subNavs) {
                tree.add(nav);
                makeNavigationTreeList(tree, navigations, nav.getId());
            }
        }
    }
}
